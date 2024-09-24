package com.example.mynotks.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Notks::class, Notes::class, ListItems::class],
    version = 1, exportSchema = false
)
abstract class NotksDatabase: RoomDatabase() {
    abstract fun notksDao(): NotksDao
    abstract fun notesDao(): NotesDao
    abstract fun listItemsDao(): ListDao

    companion object {
        @Volatile
        private var Instance: NotksDatabase? = null

        fun getDatabase(context: Context): NotksDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, NotksDatabase::class.java, "notks_database")
                    .build()
                    .also { Instance = it}
            }
        }
    }

}