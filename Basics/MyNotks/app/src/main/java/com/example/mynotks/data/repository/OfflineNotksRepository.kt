package com.example.mynotks.data.repository

import com.example.mynotks.data.Notks
import com.example.mynotks.data.NotksDao

class OfflineNotksRepository(private val notksDao: NotksDao): NotksRepository {
    override suspend fun insertNotks(notks: Notks) = notksDao.insert(notks)

    override suspend fun updateNotks(notks: Notks) = notksDao.update(notks)

    override suspend fun deleteNotks(notks: Notks) = notksDao.delete(notks)

}