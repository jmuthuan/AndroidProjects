package com.example.mynotks.data.repository

import com.example.mynotks.data.ListItems
import kotlinx.coroutines.flow.Flow


/**
 * Repository that provides insert, update, delete, and retrieve of [ListItems] from a given data source.
 */
interface ListItemsRepository {
    suspend fun insert(listItems: ListItems)

    suspend fun update(listItems: ListItems)

    suspend fun delete(listItems: ListItems)

    suspend fun getAllListItemsStream(idMain: Int): Flow<List<ListItems>>
}