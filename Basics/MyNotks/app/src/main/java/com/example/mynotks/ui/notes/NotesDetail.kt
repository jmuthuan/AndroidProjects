package com.example.mynotks.ui.notes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mynotks.R
import com.example.mynotks.ui.AppViewModelProvider
import com.example.mynotks.ui.NotksTopAppBar
import com.example.mynotks.ui.navigation.NavigationDestination
import com.example.mynotks.ui.theme.MyNotksTheme
import com.example.mynotks.ui.toColor
import kotlinx.coroutines.launch

object NotesDetailDestination: NavigationDestination {
    override val route = "notes_details"
    override val titleRes = R.string.title_res
    const val noteIdArg = "noteId"
    val routeWithArgs = "$route/{$noteIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesDetail(
    navigateBack: () -> Unit,
    navigateToUpdateScreen: (Int) -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NoteDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.detailUiState
    val coroutineScope = rememberCoroutineScope()
    val id = viewModel.getNoteId()

    val scrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
                 NotksTopAppBar(
                     title = "Note Details",
                     canNavigateBack = true,
                     navigateUp = onNavigateUp )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(
                        onClick = { navigateToUpdateScreen(id) },
                        ) {
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit Button")
                    }
                    IconButton(onClick = {
                        coroutineScope.launch {
                            viewModel.deleteNoteId(id)
                            navigateBack()
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete Button")
                    }
                })
        }
    ) { innerPadding ->
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 32.dp),
            colors = CardDefaults.cardColors(
                containerColor = uiState.backgroundColor.toColor()),//MaterialTheme.colorScheme.surfaceVariant),
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = innerPadding.calculateTopPadding() + 8.dp)
        ) {
            Text(
                text = uiState.title,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 32.sp
            )
            Text(
                text = uiState.note,
                modifier = Modifier
                    .padding(8.dp),
                fontSize = 16.sp,
                textAlign = TextAlign.Start
            )
        }
    }
}


@Preview
@Composable
fun NotesComponentPreview() {
    MyNotksTheme {
        NotesDetail({}, {}, {} )
    }
}