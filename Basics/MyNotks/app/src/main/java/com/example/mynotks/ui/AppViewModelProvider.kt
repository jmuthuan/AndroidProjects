package com.example.mynotks.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mynotks.MyNotksApplication
import com.example.mynotks.ui.home.HomeViewModel
import com.example.mynotks.ui.lists.ListDetailsViewModel
import com.example.mynotks.ui.lists.ListUpdateViewModel
import com.example.mynotks.ui.notes.NoteDetailsViewModel
import com.example.mynotks.ui.notes.NoteUpdateViewModel
import com.example.mynotks.ui.notes.NotesEntryViewModel


/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for NotesEntryViewModel
        initializer {
            NotesEntryViewModel(
                myNotksApplication().container.notesRepository,
                myNotksApplication().container.notksRepository
            )
        }

        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(myNotksApplication().container.notksRepository)
        }

        initializer {
            NoteDetailsViewModel(
                this.createSavedStateHandle(),
                myNotksApplication().container.notesRepository,
                myNotksApplication().container.notksRepository
            )
        }

        initializer {
            NoteUpdateViewModel(
                this.createSavedStateHandle(),
                myNotksApplication().container.notksRepository,
                myNotksApplication().container.notesRepository
            )
        }

        initializer {
            ListDetailsViewModel(
                this.createSavedStateHandle(),
                myNotksApplication().container.notksRepository,
                myNotksApplication().container.listItems
            )
        }

        initializer {
            ListUpdateViewModel(
                this.createSavedStateHandle(),
                myNotksApplication().container.notksRepository,
                myNotksApplication().container.listItems
            )
        }
    }
}

fun CreationExtras.myNotksApplication(): MyNotksApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as MyNotksApplication)