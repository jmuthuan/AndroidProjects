package com.jmuthuan.treely.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmuthuan.treely.data.repository.DatabaseRepository
import com.jmuthuan.treely.shared.PersonData
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
//        val data = databaseRepository.getAllData()
    }
}