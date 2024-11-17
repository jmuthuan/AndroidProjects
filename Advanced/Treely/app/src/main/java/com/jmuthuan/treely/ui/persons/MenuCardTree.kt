package com.jmuthuan.treely.ui.persons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.MutableState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.jmuthuan.treely.R

@Composable
fun MenuCardTree(
    color: Color,
    key: String,
    shouldShowDialog: MutableState<Boolean>,
    deletePersonId: MutableState<String>,
    onEditClick: (String) -> Unit,
    onDetailsClick: (String) -> Unit,
//    onDeleteClick: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.BottomEnd)) {
        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.more_horiz),
                contentDescription = stringResource(id = R.string.more_horizontal_icon),
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(
                    Color(color.component1(), color.component2(), color.component3(), 0.85f))
                .clip(RoundedCornerShape(8.dp))
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(4.dp))
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(id = R.string.edit_menu)) },
                onClick = { onEditClick(key) },
                leadingIcon = { Icon(Icons.Outlined.Edit, contentDescription = null) }
            )
            DropdownMenuItem(
                text = { Text(stringResource(id = R.string.details_menu)) },
                onClick = { onDetailsClick(key) },
                leadingIcon = { Icon(Icons.Outlined.AccountCircle, contentDescription = null) }
            )
            DropdownMenuItem(
                text = { Text(stringResource(id = R.string.delete_menu)) },
                onClick = {
                    shouldShowDialog.value = true
                    deletePersonId.value = key
//                    onDeleteClick(key)
                          },
                leadingIcon = { Icon(Icons.Outlined.Delete, contentDescription = null) }
            )
        }
    }

}