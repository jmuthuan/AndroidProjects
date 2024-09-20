package com.example.mynotks.data

data class ListsData(
    val listId: Int,
    var listTitle: String,
    var listTasks: MutableList<Pair<String, Boolean>>
)

val tasksTest = ListsData(1, "Example 1", mutableListOf(
        Pair("Task 1", false),
        Pair("Task 2", true),
        Pair("Task 3", false),
        Pair("Final Task", false)
        )
    )


