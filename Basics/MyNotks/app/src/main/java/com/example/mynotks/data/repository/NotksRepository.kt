package com.example.mynotks.data.repository

import com.example.mynotks.data.Notks


/**
 * Repository that provides insert, update, delete, and retrieve of [Notks] from a given data source.
 */
interface NotksRepository {
    suspend fun insertNotks(notks: Notks)

    suspend fun updateNotks(notks: Notks)

    suspend fun deleteNotks(notks: Notks)
}