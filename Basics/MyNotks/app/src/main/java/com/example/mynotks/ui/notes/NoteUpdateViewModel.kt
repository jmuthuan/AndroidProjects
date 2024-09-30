package com.example.mynotks.ui.notes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotks.data.Notes
import com.example.mynotks.data.Notks
import com.example.mynotks.data.TypesNotks
import com.example.mynotks.data.repository.NotesRepository
import com.example.mynotks.data.repository.NotksRepository
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
            updateUiState = NoteUpdateUiState(
                title = notksRepository.getNotks(noteId.toInt()).first().title,
                note = notesRepository.getNote(noteId.toInt()).first().notes,
                idMain = noteId.toInt()
            )
        }
    }

    suspend fun saveUpdateNote() {
        notksRepository.updateTitle(updateUiState.title, noteId.toInt())
        notesRepository.updateNote(updateUiState.note, noteId.toInt())
    }

    fun updateTitle(title: String) {
        updateUiState = NoteUpdateUiState(title, updateUiState.note, updateUiState.idMain )
    }

    fun updateNote(note: String) {
        updateUiState = NoteUpdateUiState(updateUiState.title, note, updateUiState.idMain)
    }
}

data class NoteUpdateUiState(
    var title: String = "",
    var note: String = "",
    val idMain: Int,
)