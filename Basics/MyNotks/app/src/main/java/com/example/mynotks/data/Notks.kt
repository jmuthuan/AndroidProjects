package com.example.mynotks.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "main")
data class Notks(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: TypesNotks,
    val backgroundColor: String, /*=
        colors[Random.nextInt(0, colors.size - 1 )].toHexString(),*/
    val title: String
)

enum class TypesNotks {
    NOTE, LIST
}