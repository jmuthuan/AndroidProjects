package com.example.simplecalculator.data

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class SettingsRepositoryTest {

    @get: Rule
    val tempFolder = TemporaryFolder()

    private fun newRepository(): SettingsRepository {
        val dataStore = PreferenceDataStoreFactory.create(
            scope = CoroutineScope(Dispatchers.Default),
            produceFile = { File(tempFolder.newFolder(), "settings_test.preferences_pb") }
        )
        return SettingsRepository(dataStore)
    }

    @Test
    fun settingsRepository_noPriorWrite_defaultsToSoundAndVibrationEnabled() = runBlocking {
        val repository = newRepository()

        assertTrue(repository.soundEnabled.first())
        assertTrue(repository.vibrationEnabled.first())
    }

    @Test
    fun settingsRepository_setSoundEnabledFalse_roundTripsOnNextRead() = runBlocking {
        val repository = newRepository()

        repository.setSoundEnabled(false)

        assertTrue(repository.soundEnabled.first() == false)
        //vibration setting must remain unaffected by a sound-only write
        assertTrue(repository.vibrationEnabled.first())
    }

    @Test
    fun settingsRepository_setVibrationEnabledFalse_roundTripsOnNextRead() = runBlocking {
        val repository = newRepository()

        repository.setVibrationEnabled(false)

        assertTrue(repository.vibrationEnabled.first() == false)
        //sound setting must remain unaffected by a vibration-only write
        assertTrue(repository.soundEnabled.first())
    }
}
