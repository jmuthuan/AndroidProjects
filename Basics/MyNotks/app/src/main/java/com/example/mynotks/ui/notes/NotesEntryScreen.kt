package com.example.mynotks.ui.notes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mynotks.R
import com.example.mynotks.ui.AppViewModelProvider
import com.example.mynotks.ui.NotksTopAppBar
import com.example.mynotks.ui.navigation.NavigationDestination
import com.example.mynotks.ui.theme.MyNotksTheme
import kotlinx.coroutines.launch

object NotesEntryDestination: NavigationDestination {
    override val route = "notes_entry"
    override val titleRes = R.string.title_res
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesEntryScreen(
//    title: String = "",
//    note: String = "",
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    viewModel: NotesEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var title by remember {
        mutableStateOf("")
    }

    var note by remember {
        mutableStateOf("")
    }

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
                                viewModel.saveNote(title, note)
                                navigateBack()
                            }

                    }) {
                        Icon(Icons.Filled.Check, contentDescription = "check icon")
                    }
                    IconButton(onClick = { navigateBack() }) {
                        Icon(Icons.Filled.Close, contentDescription = "close icon")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 32.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .height(300.dp)
                .padding(innerPadding)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = {
                    Text(text = "Note title")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                label = {
                    Text(text = "Write your note here")
                },
                minLines = 4,
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