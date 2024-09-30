package com.example.mynotks.data.repository

import com.example.mynotks.data.Notks
import kotlinx.coroutines.flow.Flow


/**
 * Repository that provides insert, update, delete, and retrieve of [Notks] from a given data source.
 */
interface NotksRepository {
    suspend fun insertNotks(notks: Notks)

    suspend fun updateNotks(notks: Notks)

    suspend fun updateTitle(title: String, id: Int)

    suspend fun deleteNotks(notks: Notks)

    suspend fun deleteNotksId(id: Int)
    suspend fun getLastId(): Int

    fun getNotks(id: Int): Flow<Notks>

    /**
     * Retrieve all the items from the given data source.
     */
    fun getAllNotksStream(): Flow<List<Notks>>
}