package com.example.mynotks.ui.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mynotks.R
import com.example.mynotks.ui.AppViewModelProvider
import com.example.mynotks.ui.ColorPicker
import com.example.mynotks.ui.NotksTopAppBar
import com.example.mynotks.ui.colors
import com.example.mynotks.ui.navigation.NavigationDestination
import com.example.mynotks.ui.theme.MyNotksTheme
import com.example.mynotks.ui.toColor
import kotlinx.coroutines.launch

object NotesEntryDestination: NavigationDestination {
    override val route = "notes_entry"
    override val titleRes = R.string.title_res
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    viewModel: NotesEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    val uiState = viewModel.notesUiState

//    var title by remember {
//        mutableStateOf("")
//    }
//
//    var note by remember {
//        mutableStateOf("")
//    }

//    var color by remember {
//        mutableStateOf()
//    }

    val scrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            NotksTopAppBar(
                title = "Note Entry",
                canNavigateBack = true,
                navigateUp = onNavigateUp )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                viewModel.saveNote()
                                navigateBack()
                            }
                    }) {
                        Icon(Icons.Filled.Check, contentDescription = "check icon")
                    }
                    IconButton(onClick = { navigateBack() }) {
                        Icon(Icons.Filled.Close, contentDescription = "close icon")
                    }
                    ColorPicker(
                        colors = colors,
                        onColorSelected = { viewModel.setBackgroundColor(it) })
                }
            )
        }
    ) { innerPadding ->
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 32.dp),
            colors = CardDefaults.cardColors(
                containerColor = uiState.backgroundColor.toColor()//MaterialTheme.colorScheme.surfaceVariant
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = innerPadding.calculateTopPadding() + 8.dp)
        ) {
            OutlinedTextField(
                value = uiState.title,
                onValueChange = { viewModel.setTitle(it) },
                label = {
                    Text(text = "Note title")
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.LightGray,
                    unfocusedContainerColor = uiState.backgroundColor.toColor()
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            OutlinedTextField(
                value = uiState.note,
                onValueChange = { viewModel.setNote(it) },
                label = {
                    Text(text = "Write your note here")
                },
                minLines = 10,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.LightGray,
                    unfocusedContainerColor = uiState.backgroundColor.toColor()
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }
    }

}

@Preview
@Composable
fun NotesEntryScreenPreview() {
    MyNotksTheme {
        NotesEntryScreen( {} , {})
    }
}