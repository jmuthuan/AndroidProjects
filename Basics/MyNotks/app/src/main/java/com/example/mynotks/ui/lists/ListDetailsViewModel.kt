package com.example.mynotks.ui.lists

import android.util.Log
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
            listDetailsUiState = ListDetailsUiState(
                title = notksRepository.getNotks(listId).first().title,
                tasks = listItemsRepository.getAllListItemsStream(listId).first()
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
}

data class ListDetailsUiState(
    val title: String = "",
    val tasks: List<ListItems> = mutableListOf()
)