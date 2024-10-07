package com.example.mynotks.ui.lists

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.mynotks.data.ListItems
import com.example.mynotks.data.Notks
import com.example.mynotks.data.TypesNotks
import com.example.mynotks.data.repository.ListItemsRepository
import com.example.mynotks.data.repository.NotksRepository
import com.example.mynotks.ui.toHexString

class ListEntryViewModel(
    private val notksRepository: NotksRepository,
    private val listItemsRepository: ListItemsRepository
): ViewModel() {
    /**
     * Holds current item ui state
     */
    var listEntryUiState by mutableStateOf(ListEntryUiState())
        private set

    suspend fun saveList() {
        notksRepository.insertNotks(
            Notks(
                title = listEntryUiState.title,
                type = TypesNotks.LIST,
                backgroundColor = listEntryUiState.backgroundColor,
            )
        )
        val lastId = notksRepository.getLastId()

        listEntryUiState.tasks.forEach {
            listItemsRepository.insert(
                ListItems(
                    item = it.task,
                    checked = it.checked,
                    idMain = lastId
                )
            )
        }
    }

    fun addEmptyTask() {
        //Check that last task isn't empty
        if(listEntryUiState.tasks.firstOrNull { it.task == "" } == null) {
            val aux = listEntryUiState.tasks.toMutableList()

            aux.add( NewTask(task = "", checked = false))

            listEntryUiState = listEntryUiState.copy(
                tasks = aux
            )
        }
    }

    fun updateTask(task: String, index: Int) {
        val aux = listEntryUiState.tasks.toMutableList()

        aux.set(
            index = index,
            element = NewTask(task, aux[index].checked)
        )
        listEntryUiState = listEntryUiState.copy(
            tasks = aux
        )
    }

    fun updateTitle(title: String) {
        listEntryUiState = listEntryUiState.copy(
            title = title
        )
    }

    fun updateChecked(checked: Boolean, index: Int) {
        val aux = listEntryUiState.tasks.toMutableList()

        aux.set(
            index = index,
            element = NewTask(aux[index].task, checked)
        )
        listEntryUiState = listEntryUiState.copy(
            tasks = aux
        )
    }


    fun removeTask(index: Int) {
        val aux = listEntryUiState.tasks.toMutableList()

        aux.removeAt(index)

        listEntryUiState = listEntryUiState.copy(
            tasks = aux
        )
    }

    fun setBackgroundColor(color: Color) {
        listEntryUiState = listEntryUiState.copy(
            backgroundColor = color.toHexString()
        )
    }
}

data class ListEntryUiState(
    var title: String = "",
    var tasks: MutableList<NewTask> = mutableListOf(NewTask()),
    var backgroundColor: String = Color(0xFFFFFFFF).toHexString()
)

data class NewTask(
    val task: String = "",
    var checked: Boolean = false
)