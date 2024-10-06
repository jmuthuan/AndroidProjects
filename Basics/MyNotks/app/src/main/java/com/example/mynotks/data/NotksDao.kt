package com.example.mynotks.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NotksDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(notks: Notks)

    @Update
    suspend fun update(notks: Notks)

    @Delete
    suspend fun delete(notks: Notks)

    @Query("SELECT MAX(ID) FROM main")
    suspend fun getLastId(): Int

    @Query("SELECT * FROM main")
    fun getAllNotks(): Flow<List<Notks>>

    @Query("SELECT * FROM main WHERE id = :id")
    fun getNotks(id: Int): Flow<Notks>

    @Query("DELETE FROM main WHERE id = :id")
    suspend fun deleteNotksId(id: Int)

    @Query("UPDATE main SET title = :title WHERE id = :id")
    suspend fun updateTitle(title: String, id: Int)

    @Query("UPDATE main SET backgroundColor = :color WHERE id = :id")
    suspend fun updateBackgroundColor(color: String, id: Int)
}