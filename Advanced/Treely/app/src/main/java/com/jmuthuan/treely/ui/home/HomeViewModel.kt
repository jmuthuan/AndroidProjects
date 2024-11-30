package com.jmuthuan.treely.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmuthuan.treely.data.repository.DatabaseRepository
import com.jmuthuan.treely.shared.PersonData
import com.jmuthuan.treely.utils.Gender
import com.jmuthuan.treely.utils.RelationshipType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    private val databaseRepository: DatabaseRepository
): ViewModel() {

    private var _familyData : MutableStateFlow<List<PersonData>> =
        MutableStateFlow(value = emptyList())

    var dialogMemberData by mutableStateOf(DialogMemberData(isDialogShown = false))
        private set

    val familyData: StateFlow<List<PersonData>> = _familyData.asStateFlow()

    init {
        getAllData()
    }

    fun getAllData() {
        viewModelScope.launch {
            databaseRepository.getAllData().collect{data ->
                _familyData.value = data
            }
        }
    }

    fun deleteFamilyMember(personId: String) {
        databaseRepository.deleteFamilyMember(personId)
        getAllData()
    }

    fun addRelatedMember(name: String, personId: String, backgroundColor: Color) {
        dialogMemberData = dialogMemberData.copy(
            isDialogShown = true,
            name = name,
            personId = personId,
            backgroundColor = backgroundColor
        )
    }

    fun onDismissDialog() {
        dialogMemberData = dialogMemberData.copy(
            isDialogShown = false
        )
    }
}

data class DialogMemberData(
    var isDialogShown: Boolean,
    val name: String = "",
    val personId: String = "",
    val backgroundColor: Color = Color.White
)

