package com.jmuthuan.treely.ui.persons

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmuthuan.treely.data.repository.DatabaseRepository
import com.jmuthuan.treely.shared.PersonData
import com.jmuthuan.treely.utils.Gender
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PersonEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val databaseRepository: DatabaseRepository
): ViewModel() {
    private val personId = checkNotNull(savedStateHandle[PersonEditNavigation.personId]).toString()

    var editUiState by mutableStateOf(PersonData())
        private set

    init {
        viewModelScope.launch {
            val data = databaseRepository.getMemeber(personId).first()

            editUiState = PersonData(
                    name = data.name,
                    gender = data.gender,
                    birthday = data.birthday,
                    photo = data.photo,
                    extras = data.extras,
                    location = data.location,
                    key = personId
            )
        }
    }


    fun updateName(name: String) {
        editUiState = editUiState.copy(
            name = name
        )
    }

    fun updateGender(gender: Gender) {
        editUiState = editUiState.copy(
            gender = gender
        )
    }

    fun updatePhoto(photo: String) {
        editUiState = editUiState.copy(
            photo = photo
        )
    }

    fun updateLocation(location: String) {
        editUiState = editUiState.copy(
            location = location
        )
    }

    fun updateBirthday(date: String) {
        editUiState = editUiState.copy(
            birthday = date
        )
    }

    fun updateExtras(extras: String) {
        editUiState = editUiState.copy(
            extras = extras
        )
    }

    fun savePerson() {
        databaseRepository.updateFamilyMember(
            PersonData(
                name = editUiState.name ,
                gender = editUiState.gender,
                location = editUiState.location,
                birthday = editUiState.birthday,
                extras = editUiState.extras,
                key = personId
            )
        )
    }

}