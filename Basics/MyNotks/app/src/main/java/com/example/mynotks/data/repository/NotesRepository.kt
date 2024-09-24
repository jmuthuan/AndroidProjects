package com.example.mynotks.data.repository

import com.example.mynotks.data.Notes

/**
 * Repository that provides insert, update, delete, and retrieve of [Notes] from a given data source.
 */
interface NotesRepository {
    suspend fun insertNotes(notes: Notes)

    suspend fun updateNotes(notes: Notes)

    suspend fun deleteNotes(notes: Notes)
}