package com.example.mynotks.data

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import java.util.Random

data class NotesData(
    val notesId: Int,
    var title: String,
    var notes: String
)

val notesTest = NotesData(1, "Notes Example Test",
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris nec tincidunt neque. Duis nulla lectus, tristique ac mattis quis, efficitur ac nunc. Integer mollis, dolor at dictum vulputate, arcu ipsum commodo neque, nec imperdiet diam dui feugiat libero. Donec eget tempus ante, id posuere turpis. Fusce molestie nisi tincidunt augue."
)
