package com.example.mynotks.ui.notes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.mynotks.data.Notes
import com.example.mynotks.data.Notks
import com.example.mynotks.data.TypesNotks
import com.example.mynotks.data.repository.NotesRepository
import com.example.mynotks.data.repository.NotksRepository
import com.example.mynotks.ui.toHexString

class NotesEntryViewModel(
    private val notesRepository: NotesRepository,
    private val notksRepository: NotksRepository
): ViewModel() {
    /**
     * Holds current item ui state
     */
    var notesUiState by mutableStateOf(NotesUiState())
        private set

    suspend fun saveNote() {
        notksRepository.insertNotks(
            Notks(
                type = TypesNotks.NOTE,
                title = notesUiState.title,
                backgroundColor = notesUiState.backgroundColor
            )
        )
        val lastId = notksRepository.getLastId()
        notesRepository.insertNotes(Notes(notes = notesUiState.note, idMain = lastId))
    }

//    suspend fun getNoteTitle(id: Int) {
//        notksRepository.getAllNotksStream()
//    }

    fun setBackgroundColor(color: Color) {
        notesUiState = notesUiState.copy(
            backgroundColor = color.toHexString()
        )
    }

    fun setTitle(title: String) {
        notesUiState = notesUiState.copy(
            title = title
        )
    }

    fun setNote(note: String) {
        notesUiState = notesUiState.copy(
            note = note
        )
    }
}

data class NotesUiState(
    var title: String = "",
    var note: String = "",
    private val idMain: Int = 0,
    var backgroundColor: String = Color(0xFFFFFFFF).toHexString()
)