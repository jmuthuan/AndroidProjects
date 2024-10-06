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

class NoteDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val notesRepository: NotesRepository,
    private val notksRepository: NotksRepository
): ViewModel() {
    private val noteId = Integer.parseInt(
        checkNotNull(savedStateHandle[NotesDetailDestination.noteIdArg]).toString())

    var detailUiState by mutableStateOf(NoteDetailsUiState())
        private set

    init {
            viewModelScope.launch {
                val notks = notksRepository.getNotks(noteId).first()
                detailUiState = NoteDetailsUiState(
                    title = notks.title,
                    note = notesRepository.getNote(noteId).first().notes,
                    backgroundColor = notks.backgroundColor
                )
            }
    }

    suspend fun deleteNoteId(id: Int) {
        notesRepository.deleteNotesId(id)
        notksRepository.deleteNotksId(id)
    }

    fun getNoteId(): Int {
        return noteId
    }
}

data class NoteDetailsUiState(
    val title: String = "",
    val note: String = "",
    val backgroundColor: String = Color(0xFFFFFFFF).toHexString()
)