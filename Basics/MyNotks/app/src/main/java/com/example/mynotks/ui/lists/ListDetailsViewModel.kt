package com.example.mynotks.ui.lists

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotks.data.ListItems
import com.example.mynotks.data.repository.ListItemsRepository
import com.example.mynotks.data.repository.NotksRepository
import com.example.mynotks.ui.toHexString
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ListDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val notksRepository: NotksRepository,
    private val listItemsRepository: ListItemsRepository
): ViewModel() {
    private val listId = Integer.parseInt(
        checkNotNull(savedStateHandle[ListsDetailDestination.listIdArg]).toString())

    var listDetailsUiState by mutableStateOf(ListDetailsUiState())
        private set

    init {
        viewModelScope.launch {
            val notk = notksRepository.getNotks(listId).first()
            listDetailsUiState = ListDetailsUiState(
                title = notk.title,
                tasks = listItemsRepository.getAllListItemsStream(listId).first(),
                backgroundColor = notk.backgroundColor
            )
        }
    }

    fun getListMainId(): Int {
        return listId
    }

    suspend fun deleteList(mainId: Int) {
        notksRepository.deleteNotksId(mainId)
        listItemsRepository.deleteListId(mainId)
    }

    suspend fun updateChecked(taskId: Int, checked: Boolean) {
        val aux = listDetailsUiState.tasks.toMutableList()

        aux.set(
            index = aux.indexOfFirst { it.id == taskId},
            element = ListItems(
                id = taskId,
                item = aux.first { it.id == taskId }.item,
                checked = checked,
                idMain = aux.first { it.id == taskId }.idMain
            )
        )
        listDetailsUiState = listDetailsUiState.copy(
            tasks = aux
        )
        aux.forEach {
            listItemsRepository.updateTask(
                idTask = it.id,
                task = it.item,
                checked = it.checked
            )
        }
    }
}

data class ListDetailsUiState(
    val title: String = "",
    val tasks: List<ListItems> = mutableListOf(),
    val backgroundColor: String = Color(0xFFFFFFFF).toHexString()
)