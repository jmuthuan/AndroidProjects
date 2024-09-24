package com.example.mynotks.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface ListDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(listItems: ListItems)

    @Update
    suspend fun update(listItems: ListItems)

    @Delete
    suspend fun delete(listItems: ListItems)

    @Query("SELECT * FROM list WHERE idMain = :idMain")
    suspend fun getAllListItemsStream(idMain: Int): Flow<List<ListItems>>
}