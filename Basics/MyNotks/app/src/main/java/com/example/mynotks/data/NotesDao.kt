package com.example.mynotks.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(notes: Notes)

    @Update
    suspend fun update(notes: Notes)

    @Delete
    suspend fun delete(notes: Notes)

    @Query("SELECT * FROM notes WHERE idMain = :id")
    fun getNote(id: Int): Flow<Notes>

    @Query("DELETE FROM notes WHERE idMain = :id")
    suspend fun deleteNoteId(id: Int)

    @Query("UPDATE notes SET notes = :note WHERE idMain = :id")
    suspend fun updateNote(note: String, id: Int)
}