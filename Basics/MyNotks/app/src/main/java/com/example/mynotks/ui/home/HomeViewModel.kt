package com.example.mynotks.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotks.data.Notks
import com.example.mynotks.data.repository.NotksRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel to retrieve all items in the Room database (main entity).
 */
class HomeViewModel(notksRepository: NotksRepository): ViewModel() {
    val homeUiState: StateFlow<HomeUiState> =
        notksRepository.getAllNotksStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIME_MILLIS),
                initialValue = HomeUiState()
            )

    companion object {
        private const val TIME_MILLIS = 5_000L
    }
}

/**
 * Ui State for MainBackground Component Screen
 */

data class HomeUiState( val notksList: List<Notks> = listOf() )