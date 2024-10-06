package com.example.mynotks.data.repository

import com.example.mynotks.data.Notks
import com.example.mynotks.data.NotksDao
import kotlinx.coroutines.flow.Flow

class OfflineNotksRepository(private val notksDao: NotksDao): NotksRepository {
    override suspend fun insertNotks(notks: Notks) = notksDao.insert(notks)

    override suspend fun updateNotks(notks: Notks) = notksDao.update(notks)
    override suspend fun deleteNotks(notks: Notks) = notksDao.delete(notks)

    override suspend fun getLastId() = notksDao.getLastId()

    override fun getAllNotksStream(): Flow<List<Notks>> = notksDao.getAllNotks()

    override fun getNotks(id: Int): Flow<Notks> = notksDao.getNotks(id)

    override suspend fun deleteNotksId(id: Int) = notksDao.deleteNotksId(id)

    override suspend fun updateTitle(title: String, id: Int) =
        notksDao.updateTitle(title, id)

    override suspend fun updateBackgroundColor(color: String, id: Int) =
        notksDao.updateBackgroundColor(color, id)
}