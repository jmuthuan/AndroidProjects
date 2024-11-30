package com.jmuthuan.treely.ui.persons

import android.Manifest
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.jmuthuan.treely.R
import com.jmuthuan.treely.ui.AppViewModelProvider
import com.jmuthuan.treely.ui.DatePickerFieldToModal
import com.jmuthuan.treely.ui.TreelyBottomBar
import com.jmuthuan.treely.ui.TreelyTopBar
import com.jmuthuan.treely.ui.navigation.NavigationDestination
import com.jmuthuan.treely.utils.Gender
import com.jmuthuan.treely.utils.PickPhotoIntent
import kotlinx.coroutines.launch

object PersonEntryNavigation: NavigationDestination {
    override val route = "person_entry"
    override val titleRes = R.string.person_entry_screen_title
}

@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PersonEntryViewModel = viewModel(factory = AppViewModelProvider.Factory),
    personId: String? = null,
    relationship: String? = null
) {
    if(personId != null) {
        Log.d("MTH", "personId passed between screens=> $personId")
        Log.d("MTH", "relationship passed between screens=> $relationship")
    }

    val coroutineScope = rememberCoroutineScope()

    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val currentContext = LocalContext.current


    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
//            selectedImageUri = it
            viewModel.updatePhoto(it)
        })

    val painter = rememberAsyncImagePainter(
        model = viewModel.personEntryUiState.photo,
        placeholder = painterResource(id = R.drawable.avatar_profile),
    )

    // launches camera
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = {isSaved ->
            if(isSaved) {
                viewModel.onReceive(PickPhotoIntent.OnImageSavedWith(currentContext))
            } else {
                // handle image saving error or cancellation
                viewModel.onReceive(PickPhotoIntent.OnImageSavingCanceled)
            }
        }
    )

    // launches camera permissions
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if(isGranted) {
                viewModel.onReceive(PickPhotoIntent.OnPermissionGrantedWith(currentContext))
            } else {
                // handle permission denied
                viewModel.onReceive(PickPhotoIntent.OnPermissionDenied)
            }
        })

    // this ensures that the camera is launched only once when the url of the temp file changes
    LaunchedEffect(
        key1 = viewModel.tempImageUri.value,
        block = {
        viewModel.tempImageUri.value?.let {
            cameraLauncher.launch(it)
        }
    })


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
                        viewModel.savePerson(personId, relationship)
                    }
                    navigateBack()
                },
                onCancelAction = {
                    navigateBack()
                }
            )

        },

    ) { innerPadding ->
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
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Image(
                            painter = painter,
                            contentDescription = stringResource(id = R.string.image_entry),
                            alignment = Alignment.Center,
                            contentScale = ContentScale.None,
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                        )
                        IconButton(
                            modifier = Modifier
                                .align(Alignment.BottomEnd),
//                                    .padding(end = 8.dp, bottom = 8.dp),
                            onClick = {
                                photoPickerLauncher
                                    .launch(PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.gallery_picker),
                                contentDescription = stringResource(id = R.string.edit_menu),
                            )
                        }
                        IconButton(
                            modifier = Modifier
                                .align(Alignment.BottomStart),
//                                    .padding(end = 8.dp, bottom = 8.dp),
                            onClick = {
                                // get user's permission first to use camera
                                permissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.camera_picker),
                                contentDescription = stringResource(id = R.string.edit_menu),
                            )
                        }
                    }
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
                DatePickerFieldToModal(
                    birthday = uiState.birthday,
                    enabled = true,
                    viewModelEntry = viewModel
                )

                OutlinedTextField(
                    value = uiState.location,
                    onValueChange = { viewModel.updateLocation(it) },
                    textStyle = MaterialTheme.typography.bodySmall,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = stringResource(id = R.string.location_placeholder_icon)
                        )
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
                            if (extraLabel) stringResource(id = R.string.extras_label)
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
