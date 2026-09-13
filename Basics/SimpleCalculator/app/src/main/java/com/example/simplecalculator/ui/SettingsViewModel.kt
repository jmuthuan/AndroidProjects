package com.example.simplecalculator.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplecalculator.data.SettingsRepository
import com.example.simplecalculator.data.settingsDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

data class SettingsUiState(
    val soundEnabled: Boolean = true,
    val vibrationEnabled: Boolean = true
)

/**
 * Cross-cutting, persisted, Context-dependent settings state, kept separate from
 * [CalculatorViewModel] (a purely calculator-domain, no-arg [androidx.lifecycle.ViewModel]).
 * Requires [Application] context to build its [SettingsRepository]'s DataStore, hence
 * [AndroidViewModel] rather than a plain ViewModel.
 */
class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SettingsRepository(application.settingsDataStore)

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState

    init {
        viewModelScope.launch {
            combine(
                repository.soundEnabled,
                repository.vibrationEnabled,
                ::SettingsUiState
            ).collect { _uiState.value = it }
        }
    }

    fun setSoundEnabled(enabled: Boolean) {
        viewModelScope.launch { repository.setSoundEnabled(enabled) }
    }

    fun setVibrationEnabled(enabled: Boolean) {
        viewModelScope.launch { repository.setVibrationEnabled(enabled) }
    }
}
