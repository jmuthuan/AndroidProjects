package com.example.mynotks.data

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "main")
data class Notks(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: TypesNotks,
    val backgroundColor: Color = Color(0xFFADADAD),
    val title: String
)

enum class TypesNotks {
    NOTE, LIST
}