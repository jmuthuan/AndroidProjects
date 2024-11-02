package com.jmuthuan.treely.ui.persons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jmuthuan.treely.R
import com.jmuthuan.treely.ui.AppViewModelProvider
import com.jmuthuan.treely.ui.DatePickerFieldToModal
import com.jmuthuan.treely.ui.TreelyBottomBar
import com.jmuthuan.treely.ui.TreelyTopBar
import com.jmuthuan.treely.ui.navigation.NavigationDestination
import com.jmuthuan.treely.utils.Gender
import kotlinx.coroutines.launch

object PersonEntryNavigation: NavigationDestination {
    override val route = "person_entry"
    override val titleRes = R.string.person_entry_screen_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    viewModel: PersonEntryViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    var extraLabel by mutableStateOf(false)

    val uiState = viewModel.personEntryUiState

    Scaffold(
        topBar = {
            TreelyTopBar(
                title = stringResource(id = PersonEntryNavigation.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        bottomBar = {
            TreelyBottomBar(
                onSaveAction = {
                    coroutineScope.launch {
                        viewModel.savePerson()
                    }
                    navigateBack()
                },
                onCancelAction = {
                    navigateBack()
                }
            )

        },
//        floatingActionButton = {},

    ) {innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onBackground)
                .padding(innerPadding)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
//                    .align(Alignment.Center)
            ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(192.dp)
                            .padding(horizontal = 8.dp, vertical = 16.dp)

                    ) {
                            Image(
                                painter = painterResource(id = R.drawable.avatar_profile),
                                contentDescription = stringResource(id = R.string.image_entry),
                                alignment = Alignment.Center,
                                contentScale = ContentScale.None,
                                modifier = Modifier.fillMaxSize()
                            )
                    }

                    OutlinedTextField(
                        value = uiState.name,
                        onValueChange = { viewModel.updateName(it) },
                        textStyle = MaterialTheme.typography.bodySmall,
                        leadingIcon = {
                           Icon(
                               imageVector = Icons.Filled.Person,
                               contentDescription = stringResource(id = R.string.name_placeholder_icon)
                           )
                        },
                        label = {
                            Text(text = stringResource(id = R.string.name_label))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)

                    )
                    DatePickerFieldToModal(uiState.birthday, {} )

                    OutlinedTextField(
                        value = uiState.location,
                        onValueChange = { viewModel.updateLocation(it) },
                        textStyle = MaterialTheme.typography.bodySmall,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = stringResource(id = R.string.location_placeholder_icon))
                        },
                        label = {
                            Text(text = stringResource(id = R.string.location_label))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.onPrimary,
                                shape = RoundedCornerShape(4.dp)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Face,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.padding(start = 12.dp, top = 4.dp, bottom = 4.dp)
                        )

                        RadioButton(
                            selected = uiState.gender == Gender.MALE,
                            onClick = { viewModel.updateGender(Gender.MALE) }
                        )
                        Text(
                            text = stringResource(id = R.string.gender_male),
                            style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(end = 16.dp, top = 4.dp, bottom = 4.dp)
                        )

                        RadioButton(
                            selected = uiState.gender == Gender.FEMALE,
                            onClick = { viewModel.updateGender(Gender.FEMALE) }
                        )
                        Text(
                            text = stringResource(id = R.string.gender_female),
                            style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(end = 16.dp, top = 4.dp, bottom = 4.dp)
                        )

                        RadioButton(
                            selected = uiState.gender == Gender.OTHER,
                            onClick = { viewModel.updateGender(Gender.OTHER) }
                        )
                        Text(
                            text = stringResource(id = R.string.gender_other),
                            style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(end = 8.dp, top = 4.dp, bottom = 4.dp)
                        )
                    }

                    OutlinedTextField(
                        value = uiState.extras,
                        onValueChange = { viewModel.updateExtras(it) },
                        textStyle = MaterialTheme.typography.bodySmall,
                        leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = stringResource(id = R.string.extras_placeholder_icon),
                                )
                        },
                        placeholder = {
                            Text(text = stringResource(id = R.string.extras_placeholder))
                        },
                        label = {
                            Text(
                                text =
                                if(extraLabel) stringResource(id = R.string.extras_label)
                                else stringResource(id = R.string.extras_placeholder)
                            )
                        },
                        maxLines = 10,
                        minLines = 4,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .onFocusChanged {
                                extraLabel = it.isFocused
                            }
                    )
                }
            }

    }

}
