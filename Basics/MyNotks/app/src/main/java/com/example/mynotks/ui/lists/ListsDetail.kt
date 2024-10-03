package com.example.mynotks.ui.lists

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mynotks.R
import com.example.mynotks.ui.AppViewModelProvider
import com.example.mynotks.ui.NotksTopAppBar
import com.example.mynotks.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object ListsDetailDestination: NavigationDestination {
    override val route  = "list_details"
    override val titleRes = R.string.title_res
    const val listIdArg = "listId"
    val routeWithArgs = "${route}/{$listIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListsDetail(
    navigateBack: () -> Unit,
    navigateToUpdateScreen: (Int) -> Unit,
    onNavigateUp: () -> Unit,
    viewModel: ListDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.listDetailsUiState
    val coroutineScope = rememberCoroutineScope()
    val id = viewModel.getListMainId()

//    var checked by remember { mutableStateOf(false) }
    val scrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            NotksTopAppBar(
                title = "List Details",
                canNavigateBack = true,
                navigateUp = onNavigateUp)
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
                            viewModel.deleteList(id)
                            navigateBack()
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete Button")
                    }
                }
            )
        }
    ) { innerPadding ->
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 32.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .height(400.dp)//TODO check height for maxHeigh possible
                .padding(innerPadding)
        ) {
            Text(
                text = uiState.title,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            LazyColumn {
                items(uiState.tasks) { tasks ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = tasks.checked,
                            onCheckedChange = { }
                        )
                        Text(text = tasks.item)
                    }
                }
            }
        }
    }





}