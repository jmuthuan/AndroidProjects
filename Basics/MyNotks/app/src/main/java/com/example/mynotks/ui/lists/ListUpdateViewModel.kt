package com.example.mynotks.ui.lists

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotks.data.ListItems
import com.example.mynotks.data.repository.ListItemsRepository
import com.example.mynotks.data.repository.NotksRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ListUpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val notksRepository: NotksRepository,
    private val listItemsRepository: ListItemsRepository
): ViewModel() {
    private val listId = Integer.parseInt(
        checkNotNull(savedStateHandle[ListUpdateDestination.listIdArg]).toString()
    )

    var listUpdateUiState by mutableStateOf(ListUpdateUiState())
        private set

    init {
        viewModelScope.launch {
            val tasks = loadTasks()

            listUpdateUiState = ListUpdateUiState(
                title = notksRepository.getNotks(listId).first().title,
                tasks = tasks,
                idMain = listId
            )
        }
    }

    private suspend fun loadTasks(): MutableList<Task> {
            val items = listItemsRepository.getAllListItemsStream(listId).first()

            val tasks = mutableListOf<Task>()
            items.forEach { tasks.add(Task(task = it.item, checked = it.checked, id = it.id)) }

            return tasks
    }

    fun updateTitle(title: String) {
        listUpdateUiState = listUpdateUiState.copy(
            title = title
        )
    }

    fun updateTask(task: String, taskId: Int) {
        val aux = listUpdateUiState.tasks.toMutableList()

        aux.set(
            index = aux.indexOfFirst { it.id == taskId },
            element = Task(
                task = task,
                checked = aux.first { it.id == taskId }.checked,
                id = taskId
            )
        )
        listUpdateUiState = listUpdateUiState.copy(
            tasks = aux
        )
    }

    suspend fun addEmptyTask() {
        if (listUpdateUiState.tasks.firstOrNull { it.task == "" } == null) {
            listItemsRepository.insert(
                ListItems(item = "", checked = false, idMain = listUpdateUiState.idMain)
            )

            val tasks = loadTasks()
            listUpdateUiState = listUpdateUiState.copy(
                tasks = tasks
            )
        }
        /* TODO -> add Toast to notify the user that
            can't add a task because one is already empty */
    }

    suspend fun addTask(id:Int, task: String, checked: Boolean, idMain: Int) {
        listItemsRepository.insert(
            ListItems(
                id = id,
                item = task,
                checked = checked,
                idMain = idMain
            )
        )
        val tasks = loadTasks()
        listUpdateUiState = listUpdateUiState.copy(
            tasks = tasks
        )
    }

    fun updateChecked(taskId: Int, checked: Boolean) {
        val aux = listUpdateUiState.tasks.toMutableList()

        aux.set(
            index = aux.indexOfFirst { it.id == taskId },
            element = Task(
                task = aux.first { it.id == taskId }.task,
                checked = checked,
                id = taskId
            )
        )
        listUpdateUiState = listUpdateUiState.copy(
            tasks = aux
        )
    }

    suspend fun removeTask(taskId: Int) {
        val aux = listUpdateUiState.tasks.toMutableList()

        aux.removeAt( index = aux.indexOfFirst { it.id == taskId } )

        listUpdateUiState = listUpdateUiState.copy(tasks = aux)
        listItemsRepository.deleteTask(taskId)
    }


    suspend fun saveUpdateTaskList() {
        notksRepository.updateTitle(title = listUpdateUiState.title, id = listUpdateUiState.idMain)
        listUpdateUiState.tasks.forEach {
            listItemsRepository.updateTask(
                idTask = it.id,
                task = it.task,
                checked = it.checked
            )//TODO -> check and delete tasks deleted before
        }
    }
}



data class ListUpdateUiState(
    var title: String = "",
    var tasks: MutableList<Task> = mutableListOf(),
    val idMain: Int = 0
)

data class Task(
    var task: String = "",
    var checked: Boolean = false,
    val id: Int
)