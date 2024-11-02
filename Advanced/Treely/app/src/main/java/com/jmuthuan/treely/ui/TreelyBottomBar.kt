package com.jmuthuan.treely.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jmuthuan.treely.R

@Composable
fun TreelyBottomBar(
    onSaveAction: (() -> Unit)? = null,
    onCancelAction: (() -> Unit)? = null,
    onEditAction: ((Int) -> Unit)? = null,
    onDeleteAction: ((Int)  -> Unit)? = null,
    id: Int? = null
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        tonalElevation = 4.dp,
        actions = {
            //save action
            if(onSaveAction != null){
                IconButton(
                    onClick = { onSaveAction() }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = stringResource(id = R.string.bottom_bar_save_icon)
                    )
                }
            }

            //cancel action
            if (onCancelAction != null) {
                IconButton(
                    onClick = { onCancelAction() }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(id = R.string.bottom_bar_cancel_icon)
                    )
                }
            }

            //edit action
            if(onEditAction != null) {
                IconButton(
                    onClick = { onEditAction(id!!) }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = stringResource(id = R.string.bottom_bar_edit_icon)
                    )
                }
            }

            //delete action
            if(onDeleteAction != null) {
                IconButton(
                    onClick = { onDeleteAction(id!!) }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = stringResource(id = R.string.bottom_bar_delete_icon)
                    )
                }
            }
        }
    )
}