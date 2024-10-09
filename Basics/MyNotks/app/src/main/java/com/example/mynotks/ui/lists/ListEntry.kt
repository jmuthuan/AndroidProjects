package com.example.mynotks.ui.lists

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mynotks.R
import com.example.mynotks.ui.AppViewModelProvider
import com.example.mynotks.ui.ColorPicker
import com.example.mynotks.ui.NotksSnackbar
import com.example.mynotks.ui.NotksTopAppBar
import com.example.mynotks.ui.colors
import com.example.mynotks.ui.navigation.NavigationDestination
import com.example.mynotks.ui.shadow
import com.example.mynotks.ui.theme.nanumFontfamily
import com.example.mynotks.ui.toColor
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
    viewModel: ListEntryViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    val scrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()
    val coroutineScope = rememberCoroutineScope()

    val uiState = viewModel.listEntryUiState
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = {
           SnackbarHost(snackbarHostState) {data ->
                NotksSnackbar(data.visuals.message)
           }
        },
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
                            //Check that there's no empty list
                            if(uiState.title == "" && uiState.tasks.all { it.task == "" } ) {
                                coroutineScope.launch {
                                   snackbarHostState.showSnackbar(
                                       message = "Can't save an empty list!",
                                       duration = SnackbarDuration.Short)
                                }
                            } else {
                                coroutineScope.launch {
                                    viewModel.saveList()
                                    navigateBack()
                                }
                            }
                        }) {
                        Icon(
                            Icons.Filled.Check,
                            contentDescription = "check icon",
                            tint = Color.White)
                    }
                    IconButton(onClick = { navigateBack() }) {
                        Icon(
                            Icons.Filled.Close,
                            contentDescription = "close icon",
                            tint = Color.White)
                    }
                    ColorPicker(
                        colors = colors,
                        onColorSelected = { viewModel.setBackgroundColor(it) })
                },
                containerColor = MaterialTheme.colorScheme.primary
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    //Check that there's already no empty task
                    if(uiState.tasks.firstOrNull {it.task == "" } == null) {
                        viewModel.addEmptyTask()
                    } else {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = "There's already an empty task!",
                                duration = SnackbarDuration.Short)
                        }
                    }
                },
                containerColor = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.onBackground,
                        RoundedCornerShape(12.dp)
                    )
                    .shadow(
                        color = MaterialTheme.colorScheme.tertiary,
                        offsetX = 4.dp,
                        offsetY = 4.dp,
                        blurRadius = 4.dp
                    )
                ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "add button")
            }
        }
    ) { innerPadding ->
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 32.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = uiState.backgroundColor.toColor()
            ),
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onBackground)
                .padding(horizontal = 16.dp)
                .padding(
                    top = innerPadding.calculateTopPadding() + 8.dp,
                    bottom = innerPadding.calculateBottomPadding() + 8.dp)
                .clickable {
                    //Check that there's already no empty task
                    if(uiState.tasks.firstOrNull {it.task == "" } == null) {
                        viewModel.addEmptyTask()
                    } else {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = "There's already an empty task!",
                                duration = SnackbarDuration.Short)
                        }
                    }
                },
        ) {
            OutlinedTextField(
                value = uiState.title,
                onValueChange = { viewModel.updateTitle(it) },
                label = {
                    Text(text = "List title")
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.LightGray,
                    unfocusedContainerColor = uiState.backgroundColor.toColor()
                ),
                textStyle = TextStyle(
                    fontFamily = nanumFontfamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            LazyColumn {
                items( uiState.tasks ) { task ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = task.task,
                            onValueChange = {
                                viewModel.updateTask(
                                    task = it,
                                    index = uiState.tasks.indexOf(task)
                                )
                            },
                            leadingIcon = {
                                Checkbox(
                                    checked = task.checked,
                                    onCheckedChange = {
                                        viewModel.updateChecked(
                                            checked = it,
                                            index = uiState.tasks.indexOf(task)
                                        )
                                    }
                                )
                            },
                            trailingIcon = {
                                IconButton(
                                    onClick = { viewModel.removeTask(uiState.tasks.indexOf(task)) },
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Clear,
                                        contentDescription = "delete icon"
                                    )
                                }
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.LightGray,
                                unfocusedContainerColor = uiState.backgroundColor.toColor()
                            ),
                            textStyle = TextStyle(
                                fontFamily = nanumFontfamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 24.sp
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp)
                        )
                    }
                }
            }
        }
    }
}


