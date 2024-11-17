package com.jmuthuan.treely.ui.home


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jmuthuan.treely.R
import com.jmuthuan.treely.ui.AppViewModelProvider
import com.jmuthuan.treely.ui.TreelyTopBar
import com.jmuthuan.treely.ui.navigation.NavigationDestination
import com.jmuthuan.treely.ui.persons.DeleteAlertDialog
import com.jmuthuan.treely.ui.persons.PersonCardTree


object HomeDestination: NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.home_screen_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToEntryPerson: () -> Unit,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier,
    navigateToEditPerson: (String) -> Unit,
    navigateToDetailPerson: (String) -> Unit
) {
    viewModel.getAllData()
    val family by viewModel.familyData.collectAsState()

    val shouldShowDialog = remember { mutableStateOf(false) }
    val deletePersonId = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TreelyTopBar(
                title = stringResource(id = HomeDestination.titleRes),
                canNavigateBack = false,
                navigateUp = { }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToEntryPerson() },
                shape = RoundedCornerShape(8.dp),
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 8.dp
                ),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "new family member floating button" )
            }
        }
    ) { innerPadding ->

        if(shouldShowDialog.value){
            DeleteAlertDialog(
                shouldShowDialog = shouldShowDialog,
                onConfirm = {
                    viewModel.deleteFamilyMember(personId = deletePersonId.value)
                }
            )
        }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.onBackground),
                content = {
                    items(family) { member ->
                        Log.d("MTH", "$member")
                        PersonCardTree(
                            name = member.name,
                            birthday = member.birthday.toString(),
                            gender = member.gender,
                            key = member.key,
                            onEditClick = navigateToEditPerson,
                            onDetailClick = navigateToDetailPerson,
//                            onDeleteClick = {},
                            shouldShowDialog = shouldShowDialog,
                            deletePersonId = deletePersonId
//                        modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            )
    }
}
