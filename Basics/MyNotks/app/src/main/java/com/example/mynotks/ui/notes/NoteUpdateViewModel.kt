package com.example.mynotks.ui.notes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotks.data.repository.NotesRepository
import com.example.mynotks.data.repository.NotksRepository
import com.example.mynotks.ui.toHexString
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class NoteUpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val notksRepository: NotksRepository,
    private val notesRepository: NotesRepository
): ViewModel() {

    private val noteId = checkNotNull(savedStateHandle[NotesDetailDestination.noteIdArg]).toString()

    var updateUiState by mutableStateOf(NoteUpdateUiState(idMain = noteId.toInt()))
        private set

    init {
        viewModelScope.launch {
            val notks = notksRepository.getNotks(noteId.toInt()).first()

            updateUiState = NoteUpdateUiState(
                title = notks.title,
                note = notesRepository.getNote(noteId.toInt()).first().notes,
                idMain = noteId.toInt(),
                backgroundColor = notks.backgroundColor
            )
        }
    }

    suspend fun saveUpdateNote() {
        notksRepository.updateTitle(updateUiState.title, noteId.toInt())
        notksRepository.updateBackgroundColor(updateUiState.backgroundColor, noteId.toInt())
        notesRepository.updateNote(updateUiState.note, noteId.toInt())
    }

    fun updateTitle(title: String) {
        updateUiState = NoteUpdateUiState(
            title,
            updateUiState.note,
            updateUiState.backgroundColor,
            updateUiState.idMain)
    }

    fun updateNote(note: String) {
        updateUiState = NoteUpdateUiState(
            updateUiState.title,
            note,
            updateUiState.backgroundColor,
            updateUiState.idMain)
    }

    fun setBackgroundColor(color: Color) {
        updateUiState = updateUiState.copy(
            backgroundColor = color.toHexString()
        )
    }
}

data class NoteUpdateUiState(
    var title: String = "",
    var note: String = "",
    var backgroundColor: String = Color(0xFFFFFFFF).toHexString(),
    val idMain: Int,
)