package com.example.mynotks.ui.home

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mynotks.R
import com.example.mynotks.ui.shadow


/*@Composable
@Preview
fun FloatingButton(){
    FloatingActionButton(
        onClick = {},
        shape = MaterialTheme.shapes.medium,
        containerColor = MaterialTheme.colorScheme.tertiary,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 8.dp
        )
    ) {
        Icon(
            painter = painterResource(R.drawable.baseline_add_24),
            contentDescription = null,
            tint = Color.White
        )
    }
}*/

//@Preview
//@Composable
//fun ViewMultiFloatingButton(){
//    MultiFloatingActionButton(
//        onClickAddList = {},
//        onClickAddNote = {}
//    )
//}


enum class MultiFabState {
    COLLAPSED, EXPANDED
}
class FabItem(
    val icon: Painter,
    val label: String,
    val onFabItemClicked: () -> Unit
)

@Composable
fun MultiFloatingActionButton(
    showLabels: Boolean = true,
    onClickAddNote: () -> Unit,
    onClickAddList: () -> Unit,
    onStateChanged: ((state: MultiFabState) -> Unit)? = null
) {
    var currentState by remember { mutableStateOf(MultiFabState.COLLAPSED) }
    val stateTransition: Transition<MultiFabState> =
        updateTransition(targetState = currentState, label = "")
    val stateChange: () -> Unit = {
        currentState = if (stateTransition.currentState == MultiFabState.EXPANDED) {
            MultiFabState.COLLAPSED
        } else MultiFabState.EXPANDED
        onStateChanged?.invoke(currentState)
    }
    val rotation: Float by stateTransition.animateFloat(
        transitionSpec = {
            if (targetState == MultiFabState.EXPANDED) {
                spring(stiffness = Spring.StiffnessLow)
            } else {
                spring(stiffness = Spring.StiffnessMedium)
            }
        },
        label = ""
    ) { state ->
        if (state == MultiFabState.EXPANDED) 45f else 0f
    }
    val isEnable = currentState == MultiFabState.EXPANDED

    val miniFabs = arrayListOf(
        FabItem(
            icon =  painterResource(id = R.drawable.baseline_notes_24),
            label = "",
            onFabItemClicked = onClickAddNote
        ),
        FabItem(
            icon =  painterResource(id = R.drawable.baseline_checklist_24),
            label = "",
            onFabItemClicked = onClickAddList
        )
    )

    BackHandler(isEnable) {
        currentState = MultiFabState.COLLAPSED
    }

    val modifier = if(currentState ==   MultiFabState.EXPANDED)
        Modifier
            .fillMaxSize()
            .clickable(indication = null,
                interactionSource = remember { MutableInteractionSource() }) {
                currentState = MultiFabState.COLLAPSED
            } else Modifier.fillMaxSize()

//    Box(modifier = modifier, contentAlignment = Alignment.BottomEnd) {
        Box (
            modifier = Modifier
                .fillMaxWidth(),
//                .height(400.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Bottom,
            ) {
                miniFabs.forEach { item ->
                    SmallFloatingActionButtonRow(
                        item = item,
                        stateTransition = stateTransition
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
                FloatingActionButton(
                    shape = MaterialTheme.shapes.medium,
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    onClick = {
                        stateChange()
                    },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .border(1.dp,
                            MaterialTheme.colorScheme.onBackground,
                            RoundedCornerShape(12.dp))
                        .shadow(
                            color = MaterialTheme.colorScheme.tertiary,
                            offsetX = 4.dp,
                            offsetY = 4.dp,
                            blurRadius = 4.dp
                        )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_add_24),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.rotate(rotation)
                    )
                }
            }

        }
    }

//}


@Composable
fun SmallFloatingActionButtonRow(
    item: FabItem,
    stateTransition: Transition<MultiFabState>
) {
    val alpha: Float by stateTransition.animateFloat(
        transitionSpec = {
            tween(durationMillis = 50)
        }, label = ""
    ) { state ->
        if (state == MultiFabState.EXPANDED) 1f else 0f
    }
    val scale: Float by stateTransition.animateFloat(
        label = ""
    ) { state ->
        if (state == MultiFabState.EXPANDED) 1.0f else 0f
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .alpha(animateFloatAsState((alpha), label = "").value)
            .scale(animateFloatAsState(targetValue = scale, label = "").value)
    ) {
        SmallFloatingActionButton(
            shape = MaterialTheme.shapes.small,
            onClick = { item.onFabItemClicked() },
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = Color.White
        ) {
            Icon(
                painter = item.icon,
                contentDescription = item.label
            )
        }
    }
}