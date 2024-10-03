package com.example.mynotks.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.mynotks.ui.lists.ListsShort
import com.example.mynotks.ui.navigation.NavigationDestination
import com.example.mynotks.ui.notes.NotesShort


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
    navigateToEntryNotes: () -> Unit,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    val scrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
//        bottomBar = {
//
//        },
         floatingActionButton = {
             FloatingActionButton(
                 onClick = { navigateToEntryNotes() },
                 shape = MaterialTheme.shapes.medium,
                 modifier = Modifier.padding(20.dp)
             ) {
                 Icon(
                     imageVector = Icons.Default.Add,
                     contentDescription = "Add Note or List"
                 )
             }
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
    LazyColumn(modifier = modifier) {
        items(items = noteList, key = {it.id}) {
            if(it.type == TypesNotks.NOTE) {
                NotesShort(
                    notks = it,
                    onClickNotks = onClickNotes ,
                    modifier = Modifier
                        .padding(8.dp)

                )
            } else {
                ListsShort(
                    notks = it,
                    onClickList = onClickList,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExitAlwaysBottomAppBar() {
    val scrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.Check, contentDescription = "check icon")
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.Edit, contentDescription = "edit icon")
                    }
                },
                /*TODO animate FAB - If a FAB is present,
                it detaches from the bar and remains on screen.*/
                floatingActionButton = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.AddCircle, contentDescription = "add icon")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Text(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            text = "Example of scaffold with a bottom bar. " +
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla vehicula dolor ligula, ac lacinia dui commodo nec. Aliquam erat volutpat. Phasellus vitae rhoncus lorem, vitae aliquam nunc. Donec rhoncus mattis arcu, ut aliquam nunc pellentesque ac. Maecenas sodales rhoncus suscipit. Integer sed nisi massa. Etiam a lectus massa. Quisque malesuada imperdiet ex id viverra. Quisque sit amet laoreet leo.\n" +
                    "\n" +
                    "Ut a magna ornare, consequat odio sed, tincidunt nulla. Etiam sit amet massa purus. Integer eu interdum diam, at aliquet risus. Donec lobortis id ipsum vel feugiat. Nulla aliquet malesuada venenatis. Phasellus in sapien scelerisque, ornare est eu, tristique nisi. Nunc." +
                    "Example of scaffold with a bottom bar. " +
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla vehicula dolor ligula, ac lacinia dui commodo nec. Aliquam erat volutpat. Phasellus vitae rhoncus lorem, vitae aliquam nunc. Donec rhoncus mattis arcu, ut aliquam nunc pellentesque ac. Maecenas sodales rhoncus suscipit. Integer sed nisi massa. Etiam a lectus massa. Quisque malesuada imperdiet ex id viverra. Quisque sit amet laoreet leo.\n" +
                    "\n" +
                    "Ut a magna ornare, consequat odio sed, tincidunt nulla. Etiam sit amet massa purus. Integer eu interdum diam, at aliquet risus. Donec lobortis id ipsum vel feugiat. Nulla aliquet malesuada venenatis. Phasellus in sapien scelerisque, ornare est eu, tristique nisi. Nunc."+
                    "Example of scaffold with a bottom bar. " +
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla vehicula dolor ligula, ac lacinia dui commodo nec. Aliquam erat volutpat. Phasellus vitae rhoncus lorem, vitae aliquam nunc. Donec rhoncus mattis arcu, ut aliquam nunc pellentesque ac. Maecenas sodales rhoncus suscipit. Integer sed nisi massa. Etiam a lectus massa. Quisque malesuada imperdiet ex id viverra. Quisque sit amet laoreet leo.\n" +
                    "\n" +
                    "Ut a magna ornare, consequat odio sed, tincidunt nulla. Etiam sit amet massa purus. Integer eu interdum diam, at aliquet risus. Donec lobortis id ipsum vel feugiat. Nulla aliquet malesuada venenatis. Phasellus in sapien scelerisque, ornare est eu, tristique nisi. Nunc."
        )

    }
    
}

//@Preview
//@Composable
//fun MainBackgroundPreview() {
//    MyNotksTheme {
//        MainBackground({}, {}, {})
//    }
//}