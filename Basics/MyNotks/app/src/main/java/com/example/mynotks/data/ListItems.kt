package com.example.mynotks.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list")
data class ListItems(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val item: String,
    val checked: Boolean,
    val idMain: Int
)
