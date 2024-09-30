package com.example.mynotks.data.repository

import com.example.mynotks.data.Notes
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [Notes] from a given data source.
 */
interface NotesRepository {
    suspend fun insertNotes(notes: Notes)

    suspend fun updateNotes(notes: Notes)

    suspend fun deleteNotes(notes: Notes)

    suspend fun deleteNotesId(id: Int)

    suspend fun updateNote(note: String, id: Int)

    fun getNote(id: Int): Flow<Notes>
}