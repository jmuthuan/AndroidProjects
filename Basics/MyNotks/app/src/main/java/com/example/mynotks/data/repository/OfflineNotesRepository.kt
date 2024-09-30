package com.example.mynotks.data.repository

import com.example.mynotks.data.Notes
import com.example.mynotks.data.NotesDao
import kotlinx.coroutines.flow.Flow

class OfflineNotesRepository(private val notesDao: NotesDao): NotesRepository {
    override suspend fun insertNotes(notes: Notes) = notesDao.insert(notes)

    override suspend fun updateNotes(notes: Notes) = notesDao.update(notes)

    override suspend fun deleteNotes(notes: Notes) = notesDao.delete(notes)
    override suspend fun deleteNotesId(id: Int) = notesDao.deleteNoteId(id)
    override fun getNote(id: Int): Flow<Notes> = notesDao.getNote(id)

    override suspend fun updateNote(note: String, id: Int) =
        notesDao.updateNote(note, id)
}