package com.jmuthuan.treely.ui.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.jmuthuan.treely.R
import com.jmuthuan.treely.ui.theme.TreelyTheme
import com.jmuthuan.treely.utils.ArrowShow
import com.jmuthuan.treely.utils.RelationshipType

@Composable
fun RelatedMemberDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, RelationshipType) -> Unit,
    name: String,
    backgroundColor: Color,
    key: String,
    modifier: Modifier = Modifier,
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    )
    {
        Card(
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 5.dp
            ),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.outline
            ),
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .border(width = 2.dp, color = Color.Gray, shape = RoundedCornerShape(16.dp))

        ) {
            IconButton(
                onClick = { onDismiss() },
                modifier = Modifier
                    .padding(8.dp)
                    .align(End)
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = stringResource(id = R.string.close_dialog),
                )
            }
            SkeletonRelatedMember(
                relationshipType = RelationshipType.MOTHER_FATHER,
                arrow = ArrowShow.UP,
                onConfirm = onConfirm,
                key = key,
                onDismiss = onDismiss,
                modifier = Modifier
                    .align(Start)
                    .padding(start = 16.dp)
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.offset(x = 0.dp, y = (-56).dp)
            ) {
                SkeletonRelatedMember(
                    relationshipType = null,
                    name = name,
                    arrow = ArrowShow.DOWN,
                    onConfirm = {_, _ ->  },
                    key = key,
                    onDismiss = onDismiss,
                    backgroundColor = backgroundColor,
                    modifier = Modifier
                        .padding(start = 16.dp)
                )
                Spacer(modifier = Modifier.width(32.dp))
                Column {
                    SkeletonRelatedMember(
                        relationshipType = RelationshipType.WIFE_HUSBAND_PARTNER,
                        arrow = ArrowShow.RIGHT_TOP,
                        onConfirm = onConfirm,
                        key = key,
                        onDismiss = onDismiss,
                    )
                    SkeletonRelatedMember(
                        relationshipType = RelationshipType.SISTER_BROTHER,
                        arrow = ArrowShow.RIGHT_DOWN,
                        onConfirm = onConfirm,
                        key = key,
                        onDismiss = onDismiss,
                    )
                }
            }
            SkeletonRelatedMember(
                relationshipType = RelationshipType.DAUGHTER_SON,
                arrow = ArrowShow.NONE,
                onConfirm = onConfirm,
                key = key,
                onDismiss = onDismiss,
                modifier = Modifier
                    .align(Start)
                    .padding(start = 16.dp)
                    .offset(x = 0.dp, y = (-110).dp)
            )
        }
    }
}


@Preview (showBackground = true)
@Composable
fun RelatedMemberDialogPreview() {
    TreelyTheme {
        RelatedMemberDialog(
            onDismiss = { /*TODO*/ },
            onConfirm = { _, _ -> },
            name = "Paul",
            backgroundColor = Color(0x90333333),
            key = "",
        )
    }

}