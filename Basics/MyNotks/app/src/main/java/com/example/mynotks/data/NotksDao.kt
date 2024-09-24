package com.example.mynotks.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
interface NotksDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(notks: Notks)

    @Update
    suspend fun update(notks: Notks)

    @Delete
    suspend fun delete(notks: Notks)
}