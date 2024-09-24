package com.example.mynotks.data.repository

import com.example.mynotks.data.Notes
import com.example.mynotks.data.NotesDao

class OfflineNotesRepository(private val notesDao: NotesDao): NotesRepository {
    override suspend fun insertNotes(notes: Notes) = notesDao.insert(notes)

    override suspend fun updateNotes(notes: Notes) = notesDao.update(notes)

    override suspend fun deleteNotes(notes: Notes) = notesDao.delete(notes)
}