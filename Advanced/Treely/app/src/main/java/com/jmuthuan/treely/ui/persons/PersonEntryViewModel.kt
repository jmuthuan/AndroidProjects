package com.jmuthuan.treely.ui.persons

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.jmuthuan.treely.data.repository.DatabaseRepository
import com.jmuthuan.treely.shared.PersonData
import com.jmuthuan.treely.utils.Gender
import java.util.Date

class PersonEntryViewModel(
    private val databaseRepository: DatabaseRepository
): ViewModel() {
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

    fun updatePhoto(photo: String) {
        personEntryUiState = personEntryUiState.copy(
            photo = photo
        )
    }

    fun updateLocation(location: String) {
        personEntryUiState = personEntryUiState.copy(
            location = location
        )
    }

    fun updateBirthday(date: Date) {
        personEntryUiState = personEntryUiState.copy(
            birthday = date
        )
    }

    fun updateExtras(extras: String) {
        personEntryUiState = personEntryUiState.copy(
            extras = extras
        )
    }

    fun savePerson() {
        databaseRepository.addFamilyMember(
            PersonData(
                name = personEntryUiState.name ,
                gender = personEntryUiState.gender,
                location = personEntryUiState.location,
                birthday = personEntryUiState.birthday,
                extras = personEntryUiState.extras
            )
        )
    }

}
