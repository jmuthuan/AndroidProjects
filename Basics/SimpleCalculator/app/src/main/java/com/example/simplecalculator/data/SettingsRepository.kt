package com.example.simplecalculator.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Single DataStore instance for app-wide, persisted user preferences (Sound/Vibration
 * feedback toggles). Kept separate from [com.example.simplecalculator.ui.CalculatorViewModel]'s
 * purely in-memory, calculator-domain [com.example.simplecalculator.ui.CalculatorUiState].
 */
internal val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

/**
 * Constructed from a [DataStore] (not a [Context] directly) so it can be unit-tested against
 * a temp-file-backed DataStore without Android framework/Robolectric.
 */
class SettingsRepository(private val dataStore: DataStore<Preferences>) {

    companion object {
        val SOUND_ENABLED = booleanPreferencesKey("sound_enabled")
        val VIBRATION_ENABLED = booleanPreferencesKey("vibration_enabled")
    }

    val soundEnabled: Flow<Boolean> = dataStore.data.map { it[SOUND_ENABLED] ?: true }

    val vibrationEnabled: Flow<Boolean> = dataStore.data.map { it[VIBRATION_ENABLED] ?: true }

    suspend fun setSoundEnabled(enabled: Boolean) {
        dataStore.edit { it[SOUND_ENABLED] = enabled }
    }

    suspend fun setVibrationEnabled(enabled: Boolean) {
        dataStore.edit { it[VIBRATION_ENABLED] = enabled }
    }
}
