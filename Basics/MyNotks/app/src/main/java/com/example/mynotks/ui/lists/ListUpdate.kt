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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
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
import com.example.mynotks.ui.theme.onBackgroundLight
import com.example.mynotks.ui.theme.primaryDark
import com.example.mynotks.ui.toColor
import kotlinx.coroutines.launch

object ListUpdateDestination: NavigationDestination {
    override val route = "list_update"
    override val titleRes = R.string.title_res
    const val listIdArg = "listId"
    val routeWithArgs = "${route}/{$listIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListUpdate(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    navigateToStart: () -> Unit,
    viewModel: ListUpdateViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.listUpdateUiState

    val scrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()
    val coroutineScope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            NotksTopAppBar(
                title = stringResource(id = R.string.top_bar_list_update),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState) {data ->
                NotksSnackbar(data.visuals.message)
            }
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                viewModel.saveUpdateTaskList()
                                navigateToStart()
                            }
                        }) {
                        Icon(
                            Icons.Filled.Check,
                            contentDescription = stringResource(id = R.string.check_icon),
                            tint = Color.White)
                    }
                    IconButton(onClick = { navigateBack() }) {
                        Icon(
                            Icons.Filled.Close,
                            contentDescription = stringResource(id = R.string.cancel_icon),
                            tint = Color.White)
                    }
                    ColorPicker(
                        colors = colors,
                        onColorSelected = { viewModel.setBackgroundColor(it)}
                    )
                },
                containerColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .shadow(
                        color = primaryDark,
                        offsetY = (-2).dp,
                        blurRadius = 4.dp
                    )
                    .background(MaterialTheme.colorScheme.onBackground)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        //Check that there's already no empty task
                        if (uiState.tasks.firstOrNull { it.task == "" } == null) {
                            viewModel.addEmptyTask()
                        } else {
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
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.add_icon_description)
                )
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
                        top = innerPadding.calculateTopPadding() + 16.dp,
                        bottom = innerPadding.calculateBottomPadding() +16.dp
                    )
                    .clickable {
                        coroutineScope.launch {
                            //Check that there's already no empty task
                            if (uiState.tasks.firstOrNull { it.task == "" } == null) {
                                viewModel.addEmptyTask()
                            } else {
                                snackbarHostState.showSnackbar(
                                    message = "There's already an empty task!",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    },
            ) {
                OutlinedTextField(
                    value = uiState.title,
                    onValueChange = { viewModel.updateTitle(it) },
                    label = {
                        Text(text = stringResource(id = R.string.placeholder_list_title))
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.LightGray,
                        unfocusedContainerColor = uiState.backgroundColor.toColor()
                    ),
                    textStyle = TextStyle(
                        fontFamily = nanumFontfamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                        color = onBackgroundLight
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                LazyColumn {
                    items( uiState.tasks ) { tasks ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextField(
                                value = tasks.task,
                                onValueChange = {
                                    coroutineScope.launch {
                                        viewModel.updateTask(it, tasks.id)
                                    }
                                },
                                leadingIcon = {
                                    Checkbox(
                                        checked = tasks.checked,
                                        onCheckedChange = {
                                            viewModel.updateChecked(tasks.id, it)
                                        }
                                    )
                                },
                                trailingIcon = {
                                    IconButton(
                                        onClick = {
                                            coroutineScope.launch {
                                                viewModel.removeTask(tasks.id)
                                            }
                                       },
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Clear,
                                            contentDescription = stringResource(id = R.string.delete_icon_description)
                                        )
                                    }
                                },
                                colors = TextFieldDefaults.colors(
                                    unfocusedContainerColor = uiState.backgroundColor.toColor(),
                                    focusedContainerColor = Color.LightGray),
                                textStyle = TextStyle(
                                    fontFamily = nanumFontfamily,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 24.sp,
                                    color = onBackgroundLight
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 4.dp)
                                    .onFocusChanged {
                                        if (!it.isFocused) {
                                            coroutineScope.launch {
                                                viewModel.updateTask(tasks.task, tasks.id)
                                            }
                                        }
                                    }
                            )
                        }
                    }
                }
            }
        }
   }