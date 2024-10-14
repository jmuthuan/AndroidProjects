package com.example.mynotks.ui.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
import com.example.mynotks.ui.theme.MyNotksTheme
import com.example.mynotks.ui.theme.nanumFontfamily
import com.example.mynotks.ui.theme.onBackgroundLight
import com.example.mynotks.ui.theme.primaryDark
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

    val scrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = {
            SnackbarHost(snackbarHostState) {
                NotksSnackbar("Can't save an empty note!")
            }
        },
        topBar = {
            NotksTopAppBar(
                title = stringResource(id = R.string.top_bar_note_entry),
                canNavigateBack = true,
                navigateUp = onNavigateUp )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(
                        onClick = {
                            //Check that there's no empty note
                            if(uiState.title == "" && uiState.note == "" ) {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("")
                                }
                            } else {
                                coroutineScope.launch {
                                    viewModel.saveNote()
                                }
                                navigateBack()
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
                        onColorSelected = { viewModel.setBackgroundColor(it) })
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
        }
    ) { innerPadding ->
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 32.dp),
            colors = CardDefaults.cardColors(
                containerColor = uiState.backgroundColor.toColor()
            ),
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onBackground)
                .padding(horizontal = 16.dp)
                .padding(
                    top = innerPadding.calculateTopPadding() + 16.dp,
                    bottom = innerPadding.calculateBottomPadding() + 16.dp
                )
        ) {
            OutlinedTextField(
                value = uiState.title,
                onValueChange = { viewModel.setTitle(it) },
                label = {
                    Text(
                        text = stringResource(id = R.string.placeholder_note_title),
                        color = MaterialTheme.colorScheme.onBackground
                    )
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
                onValueChange = { viewModel.setNote(it) },
                label = {
                    Text(
                        text = stringResource(id = R.string.placeholder_note_body),
                        color = MaterialTheme.colorScheme.onBackground
                        )
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