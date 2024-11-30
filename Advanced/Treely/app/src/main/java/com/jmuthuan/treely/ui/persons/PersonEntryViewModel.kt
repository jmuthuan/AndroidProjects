package com.jmuthuan.treely.ui.persons

import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmuthuan.treely.BuildConfig
import com.jmuthuan.treely.data.repository.DatabaseRepository
import com.jmuthuan.treely.shared.PersonData
import com.jmuthuan.treely.utils.Gender
import com.jmuthuan.treely.utils.PickPhotoIntent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import kotlin.coroutines.CoroutineContext

class PersonEntryViewModel(
    private val databaseRepository: DatabaseRepository,
    private val coroutineContext: CoroutineContext = Dispatchers.Default
): ViewModel() {

    var tempImageUri = mutableStateOf<Uri?>(null)
    var personEntryUiState by mutableStateOf(PersonData())
        private set

    fun updateName(name: String) {
        personEntryUiState = personEntryUiState.copy(
            name = name
        )
    }

    fun updateGender(gender: Gender) {
        personEntryUiState = personEntryUiState.copy(
            gender = gender
        )
    }

    fun updatePhoto(photo: Uri?) {
        personEntryUiState = personEntryUiState.copy(
            photo = photo.toString()
        )
    }

    fun updateLocation(location: String) {
        personEntryUiState = personEntryUiState.copy(
            location = location
        )
    }

    fun updateBirthday(date: String) {
        personEntryUiState = personEntryUiState.copy(
            birthday = date
        )
    }

    fun updateExtras(extras: String) {
        personEntryUiState = personEntryUiState.copy(
            extras = extras
        )
    }

    fun savePerson(personId: String?, relationship: String?) {
        val relatioshipData =
            if(personId != null && relationship != null) {
                RelationshipData(personId, relationship)}
            else null



        databaseRepository.addFamilyMember(
            PersonData(
                name = personEntryUiState.name ,
                gender = personEntryUiState.gender,
                location = personEntryUiState.location,
                birthday = personEntryUiState.birthday,
                extras = personEntryUiState.extras
            ),
            relatioshipData
        )
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun onReceive(intent: Intent) = viewModelScope.launch(coroutineContext) {
        when(intent) {
            is PickPhotoIntent.OnPermissionGrantedWith -> {
                // Create an empty image file in the app's cache directory
                val tempFile = File.createTempFile(
                    "temp_image_file_",
                    ".jpg",
                    intent.compositionContext.cacheDir
                )

                // Create sandboxed url for this temp file - needed for the camera API
                val uri = FileProvider.getUriForFile(
                    intent.compositionContext,
                    "${BuildConfig.APPLICATION_ID}.provider",
                    tempFile
                )

//                personEntryUiState = personEntryUiState.copy(
//                    photo = uri
//                )
                tempImageUri.value = uri
            }
            is PickPhotoIntent.OnPermissionDenied -> {
                Log.d("MTH", "User did not grant permission to use the camera") }

            is PickPhotoIntent.OnImageSavedWith -> {
                val tempImageUrl = tempImageUri.value//personEntryUiState.photo
//                if(tempImageUrl != null) {
//                    val source = ImageDecoder.createSource(
//                        intent.compositionContext.contentResolver,
//                        tempImageUrl)
//                }
                personEntryUiState = personEntryUiState.copy(
                    photo = tempImageUrl.toString()
                )
            }
            is PickPhotoIntent.OnImageSavingCanceled -> {
//                personEntryUiState = personEntryUiState.copy(
//                    photo = null
//                )
                tempImageUri.value = null
            }
        }
    }

}

data class RelationshipData(
    val personId: String,
    val relationship: String
)
