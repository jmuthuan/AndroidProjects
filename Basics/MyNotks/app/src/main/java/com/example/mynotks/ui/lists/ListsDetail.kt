package com.example.mynotks.ui.lists

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mynotks.R
import com.example.mynotks.ui.AppViewModelProvider
import com.example.mynotks.ui.DeleteAlertDialog
import com.example.mynotks.ui.NotksTopAppBar
import com.example.mynotks.ui.navigation.NavigationDestination
import com.example.mynotks.ui.shadow
import com.example.mynotks.ui.theme.nanumFontfamily
import com.example.mynotks.ui.theme.onBackgroundLight
import com.example.mynotks.ui.theme.primaryDark
import com.example.mynotks.ui.toColor
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

    val scrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()

    val shouldShowDialog = remember { mutableStateOf(false)}

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            NotksTopAppBar(
                title = stringResource(id = R.string.top_bar_list_detail),
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
                    IconButton(
                        onClick = {
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
        ) {
            if (shouldShowDialog.value) {
                DeleteAlertDialog(
                    shouldShowDialog = shouldShowDialog,
                    onConfirm = {
                        coroutineScope.launch {
                            viewModel.deleteList(id)
                            navigateBack()
                        }
                    }
                )
            }
            Text(
                text = uiState.title,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontFamily = nanumFontfamily,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = onBackgroundLight
            )
            LazyColumn {
                items(uiState.tasks) { tasks ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = tasks.checked,
                            onCheckedChange = {
                                coroutineScope.launch {
                                    viewModel.updateChecked(tasks.id, it)
                                }
                            },
                            modifier = modifier
                        )
                        Text(
                            text = tasks.item,
                            textAlign = TextAlign.Center,
                            fontFamily = nanumFontfamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 24.sp,
                            color = onBackgroundLight,
                            modifier = modifier
                        )
                    }
                }
            }
        }
    }
}