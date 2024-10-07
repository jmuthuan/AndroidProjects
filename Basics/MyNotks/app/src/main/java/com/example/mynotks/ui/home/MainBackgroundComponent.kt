package com.example.mynotks.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mynotks.R
import com.example.mynotks.data.Notks
import com.example.mynotks.data.TypesNotks
import com.example.mynotks.ui.AppViewModelProvider
import com.example.mynotks.ui.ColorPicker
import com.example.mynotks.ui.colors
import com.example.mynotks.ui.lists.ListsShort
import com.example.mynotks.ui.navigation.NavigationDestination
import com.example.mynotks.ui.notes.NotesShort
import com.example.mynotks.ui.toColor


/**
 * Entry route for Home screen
 */
object HomeDestination: NavigationDestination{
    override val route = "home"
    override val titleRes = R.string.title_res
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainBackground(
    navigateToNoteDetails: (Int) -> Unit,
    navigateToListDetails: (Int) -> Unit,
    navigateToEntryList: () -> Unit,
    navigateToEntryNotes: () -> Unit,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    val scrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = {
             MultiFloatingActionButton(
//                 fabIcon = ,
//                 items = ,
                 onClickAddNote = navigateToEntryNotes,
                 onClickAddList = navigateToEntryList
             )
         }
    ) { innerPadding ->
        HomeBody(
            notksList = homeUiState.notksList,
            onClickNotes = navigateToNoteDetails,
            onClickList = navigateToListDetails,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
fun HomeBody(
    notksList: List<Notks>,
    onClickNotes: (Int)-> Unit,
    onClickList: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier//.height(500.dp)
    ) {
        if (notksList.isEmpty()) {
            Text(
                text = "Oops! You haven't added any notes or lists",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
        } else {
            NotesAndLists(
                noteList = notksList,
                onClickNotes = onClickNotes,
                onClickList =  onClickList,
                modifier = Modifier.padding(horizontal = 8.dp))
        }
    }
}

@Composable
fun NotesAndLists(
    noteList: List<Notks>,
    onClickNotes: (Int) -> Unit,
    onClickList: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 4.dp,
//        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        content = {
            items(noteList) {
                if (it.type == TypesNotks.NOTE) {
                    NotesShort(
                        notks = it,
                        onClickNotks = onClickNotes,
                        backgroundColor = it.backgroundColor.toColor() ,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                } else {
                    ListsShort(
                        notks = it,
                        onClickList = onClickList,
                        backgroundColor = it.backgroundColor.toColor(),
                        modifier = Modifier
                            .padding(8.dp)
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxSize())
    }

