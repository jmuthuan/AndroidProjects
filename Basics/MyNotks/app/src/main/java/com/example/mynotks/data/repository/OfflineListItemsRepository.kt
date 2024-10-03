package com.example.mynotks.data.repository

import com.example.mynotks.data.ListDao
import com.example.mynotks.data.ListItems
import kotlinx.coroutines.flow.Flow

class OfflineListItemsRepository(private val listItemsDao: ListDao): ListItemsRepository {
    override suspend fun insert(listItems: ListItems) = listItemsDao.insert(listItems)

    override suspend fun update(listItems: ListItems) = listItemsDao.update(listItems)

    override suspend fun delete(listItems: ListItems) = listItemsDao.delete(listItems)

    override suspend fun getAllListItemsStream(idMain: Int): Flow<List<ListItems>> =
        listItemsDao.getAllListItemsStream(idMain)

    override suspend fun deleteListId(idMain: Int) = listItemsDao.deleteListId(idMain)

    override suspend fun updateTask(idTask: Int, task: String, checked: Boolean) =
        listItemsDao.updateTask(idTask, task, checked)

    override suspend fun deleteTask(id: Int) = listItemsDao.deleteTask(id)

}