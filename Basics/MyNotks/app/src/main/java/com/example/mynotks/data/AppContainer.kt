package com.example.mynotks.data

import android.content.Context
import com.example.mynotks.data.repository.ListItemsRepository
import com.example.mynotks.data.repository.NotesRepository
import com.example.mynotks.data.repository.NotksRepository
import com.example.mynotks.data.repository.OfflineListItemsRepository
import com.example.mynotks.data.repository.OfflineNotesRepository
import com.example.mynotks.data.repository.OfflineNotksRepository

/**
 * App container for Dependency injection.
 */

interface AppContainer {
    val notksRepository: NotksRepository
    val notesRepository: NotesRepository
    val listItems: ListItemsRepository
}

/**
 * [AppContainer] implementation that provides instance of
 * [OfflineNotksRepository], [OfflineNotesRepository] and [OfflineListItemsRepository]
 */

class AppDataContainer(private val context: Context): AppContainer {
    /**
     * Implementation for [NotksRepository]
     */
    override val notksRepository: NotksRepository by lazy {
        OfflineNotksRepository(NotksDatabase.getDatabase(context).notksDao())
    }

    override val notesRepository: NotesRepository by lazy {
        OfflineNotesRepository(NotksDatabase.getDatabase(context).notesDao())
    }

    override val listItems: ListItemsRepository by lazy {
        OfflineListItemsRepository(NotksDatabase.getDatabase(context).listItemsDao())
    }

}