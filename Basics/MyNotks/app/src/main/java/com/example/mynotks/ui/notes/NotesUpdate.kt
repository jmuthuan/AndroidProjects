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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.mynotks.ui.NotksTopAppBar
import com.example.mynotks.ui.colors
import com.example.mynotks.ui.navigation.NavigationDestination
import com.example.mynotks.ui.theme.nanumFontfamily
import com.example.mynotks.ui.theme.onBackgroundLight
import com.example.mynotks.ui.toColor
import kotlinx.coroutines.launch

object NotesUpdateDestination: NavigationDestination {
    override val route = "notes_update"
    override val titleRes = R.string.title_res
    private const val noteIdArg = "noteId"
    val routeWithArgs = "${route}/{$noteIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteUpdate(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    navigateToStart: () -> Unit,
    viewModel: NoteUpdateViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.updateUiState

    val scrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            NotksTopAppBar(
                title = "Note Update",
                canNavigateBack = true,
                navigateUp = onNavigateUp )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                viewModel.saveUpdateNote()
                                navigateToStart()
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
                        onColorSelected = { viewModel.setBackgroundColor(it)}
                    )
                },
                containerColor = MaterialTheme.colorScheme.primary
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
                .background(MaterialTheme.colorScheme.onBackground)
                .padding(horizontal = 16.dp, vertical = innerPadding.calculateTopPadding() + 8.dp)
        ) {
            OutlinedTextField(
                value = uiState.title,
                onValueChange = { viewModel.updateTitle(it) },
                label = {
                    Text(text = "Note title")
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.LightGray,
                    unfocusedContainerColor = uiState.backgroundColor.toColor()
                ),
                textStyle = TextStyle(
                    fontSize = 32.sp,
                    fontFamily = nanumFontfamily,
                    fontWeight = FontWeight.Bold,
                    color = onBackgroundLight
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            OutlinedTextField(
                value = uiState.note,
                onValueChange = { viewModel.updateNote(it) },
                label = {
                    Text(text = "Write your note here")
                },
                minLines = 10,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.LightGray,
                    unfocusedContainerColor = uiState.backgroundColor.toColor()
                ),
                textStyle = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = nanumFontfamily,
                    fontWeight = FontWeight.Normal,
                    color = onBackgroundLight
                ),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
            )
        }
    }

}