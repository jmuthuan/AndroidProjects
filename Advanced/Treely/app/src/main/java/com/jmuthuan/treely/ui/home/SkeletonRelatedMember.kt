package com.jmuthuan.treely.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jmuthuan.treely.R
import com.jmuthuan.treely.ui.theme.TreelyTheme
import com.jmuthuan.treely.utils.ArrowShow
import com.jmuthuan.treely.utils.RelationshipType

@Composable
fun SkeletonRelatedMember(
    relationshipType: RelationshipType?,
    name: String? = null,
    avatar: Painter? = null,
    arrow: ArrowShow,
    onConfirm: (String, RelationshipType) -> Unit,
    key: String,
    onDismiss: () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    modifier: Modifier = Modifier
) {
    var text = when (relationshipType) {
            RelationshipType.MOTHER_FATHER -> stringResource(id = R.string.add_mother_father)
            RelationshipType.DAUGHTER_SON -> stringResource(id = R.string.add_daughter_son)
            RelationshipType.WIFE_HUSBAND_PARTNER -> stringResource(id = R.string.add_wife_husband)
            RelationshipType.SISTER_BROTHER -> stringResource(id = R.string.add_sister_brother)
            null -> name ?: ""
        }

    Column(
        modifier = modifier
            .width(160.dp)
            .fillMaxWidth()
            .padding(bottom = 32.dp)
            .drawBehind {
                if(arrow != ArrowShow.NONE) {
                    var pathLine = getPath(this, arrow)//Path()
                    val linePoints = getLinePoints(this, arrow)
                    val offsetCircle = getOffsetCircle(this, arrow)

                    drawCircle(
                        color = Color.Black,
                        radius = 4.dp.toPx(),
                        center =  offsetCircle
                    )

                    drawLine(
                        start = linePoints[0],
                        end = linePoints[1],
                        color = Color.Black,
                        strokeWidth = 2.dp.toPx()
                    )

                    drawPath(
                        path = pathLine, color = Color.Black,
                    )
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Card(
            modifier = Modifier.clickable {
                if (relationshipType != null) {
                    onConfirm(key, relationshipType)
                    onDismiss()
                }
            },
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor.copy(alpha = 0.8f)
            )

        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .width(160.dp)
                    .padding(4.dp)
                
            ) {
                Image(
                    painter = painterResource(id = R.drawable.avatar_profile),
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .padding(start = 8.dp, top = 8.dp)
                        .clip(CircleShape),
                    alignment = Alignment.BottomStart
                )
                Spacer(modifier = Modifier.weight(0.1f))
                Text(
                    text = text,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .width(72.dp)
                        .padding(bottom = 8.dp, end = 8.dp)
                        .align(Top),
                    textAlign = TextAlign.End
                )
            }
        }

//        Spacer(modifier = Modifier.height(32.dp))

//        if(arrow != ArrowShow.NONE) {
//            ArrowDraw(arrowShow = arrow)
//        }
    }
}

@Preview (showBackground = true)
@Composable
fun SkeletonPreview() {
    TreelyTheme {
        SkeletonRelatedMember(
            relationshipType = null,
            name = "Add wife\nhusband",
            arrow = ArrowShow.DOWN,
            onConfirm =  { _ , _ ->  },
            key = "",
            onDismiss = {},
            modifier = Modifier.width(144.dp)
        )
    }
}