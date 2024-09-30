package com.example.mynotks.ui.notes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mynotks.data.Notes
import com.example.mynotks.data.Notks
import com.example.mynotks.data.TypesNotks
import com.example.mynotks.data.repository.NotesRepository
import com.example.mynotks.data.repository.NotksRepository

class NotesEntryViewModel(
    private val notesRepository: NotesRepository,
    private val notksRepository: NotksRepository
): ViewModel() {
    /**
     * Holds current item ui state
     */
    var notesUiState by mutableStateOf(NotesUiState())
        private set

    suspend fun saveNote(title: String, note: String) {
        notksRepository.insertNotks(Notks(type = TypesNotks.NOTE, title = title))
        val lastId = notksRepository.getLastId()
        notesRepository.insertNotes(Notes(notes = note, idMain = lastId))
    }

    suspend fun getNoteTitle(id: Int) {
        notksRepository.getAllNotksStream()
    }

}

data class NotesUiState(
    private val title: String = "",
    private val note: String = "",
    private val idMain: Int = 0
)