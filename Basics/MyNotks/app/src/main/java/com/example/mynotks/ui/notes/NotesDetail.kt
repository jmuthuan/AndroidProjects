package com.example.mynotks.ui.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mynotks.R
import com.example.mynotks.ui.AppViewModelProvider
import com.example.mynotks.ui.DeleteAlertDialog
import com.example.mynotks.ui.NotksTopAppBar
import com.example.mynotks.ui.navigation.NavigationDestination
import com.example.mynotks.ui.shadow
import com.example.mynotks.ui.theme.MyNotksTheme
import com.example.mynotks.ui.theme.nanumFontfamily
import com.example.mynotks.ui.theme.onBackgroundLight
import com.example.mynotks.ui.theme.primaryDark
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

    val shouldShowDialog = remember { mutableStateOf(false) }

    val scrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
                 NotksTopAppBar(
                     title = stringResource(id = R.string.top_bar_note_detail),
                     canNavigateBack = true,
                     navigateUp = onNavigateUp)
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(
                        onClick = { navigateToUpdateScreen(id) },
                        ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = stringResource(id = R.string.edit_icon_description),
                            tint = Color.White)
                    }
                    IconButton(onClick = {
                        shouldShowDialog.value = true
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = stringResource(id = R.string.delete_icon_description),
                            tint = Color.White)
                    }
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
            elevation = CardDefaults.cardElevation(defaultElevation = 32.dp),
            colors = CardDefaults.cardColors(
                containerColor = uiState.backgroundColor.toColor()),
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onBackground)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(
                    top = innerPadding.calculateTopPadding() + 16.dp,
                    bottom = innerPadding.calculateBottomPadding() + 16.dp
                )
        ) {
            if (shouldShowDialog.value) {
                DeleteAlertDialog(
                    shouldShowDialog = shouldShowDialog,
                    onConfirm = {
                        coroutineScope.launch {
                            viewModel.deleteNoteId(id)
                            navigateBack()
                        }
                    }
                )
            }

            Text(
                text = uiState.title,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 32.sp,
                    fontFamily = nanumFontfamily,
                    fontWeight = FontWeight.Bold,
                    color = onBackgroundLight
                ),
            )
            Text(
                text = uiState.note,
                modifier = Modifier
                    .padding(8.dp),
                textAlign = TextAlign.Start,
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = nanumFontfamily,
                    fontWeight = FontWeight.Normal,
                    color = onBackgroundLight
                )
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