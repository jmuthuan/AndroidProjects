package com.example.mynotks.ui.lists

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mynotks.R
import com.example.mynotks.ui.AppViewModelProvider
import com.example.mynotks.ui.ColorPicker
import com.example.mynotks.ui.NotksTopAppBar
import com.example.mynotks.ui.colors
import com.example.mynotks.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch


object ListEntryNavigation: NavigationDestination {
    override val route = "list_entry"
    override val titleRes = R.string.title_res
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListEntry(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
//    navigateToStart: () -> Unit,
    viewModel: ListEntryViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {

    val scrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()
    val coroutineScope = rememberCoroutineScope()

    val uiState = viewModel.listEntryUiState

//    var title by remember {
//        mutableStateOf("")
//    }
//
//    var tasks by remember {
//        mutableStateOf( mutableListOf("") )
//    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            NotksTopAppBar(
                title = "List Entry",
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                viewModel.saveList()
                                navigateBack()
                            }
                        }) {
                        Icon(Icons.Filled.Check, contentDescription = "check icon")
                    }
                    IconButton(onClick = { navigateBack() }) {
                        Icon(Icons.Filled.Close, contentDescription = "close icon")
                    }
                    ColorPicker(colors = colors, onColorSelected = {})
                }
//                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.addEmptyTask()
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "add button")
            }
        }
    ) { innerPadding ->
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 32.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .height(1000.dp)//TODO check height for maxHeight possible
                .padding(innerPadding)
        ) {
            OutlinedTextField(
                value = uiState.title,
                onValueChange = { viewModel.updateTitle(it) },
                label = {
                    Text(text = "List title")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            LazyColumn {
                items( uiState.tasks ) { task ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = task.task,//task.task,
                            onValueChange = {
                                viewModel.updateTask(
                                    task = it,
                                    checked = false,
                                    index = uiState.tasks.indexOf(task)
                                )
                                            },
                            leadingIcon = {
                                Checkbox(
                                    checked = false/*task.checked*/,
                                    onCheckedChange = { /*task.checked = it*/ }
                                )
                            },
                            trailingIcon = {
                                IconButton(
                                    onClick = { /*tasks.removeAt(tasks.indexOf(task))*/ },
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Clear,
                                        contentDescription = "delete icon"
                                    )
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
//                                .onFocusChanged {
//                                    if(!it.isFocused) {
//                                        coroutineScope.launch {
//                                            viewModel.updateTask(task.task, task.id)
//                                        }
//                                    }
//                                }
                        )
                    }
                }
            }
        }
    }
}


