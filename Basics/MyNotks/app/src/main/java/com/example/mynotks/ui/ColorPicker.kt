package com.example.mynotks.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mynotks.R
import com.example.mynotks.ui.theme.MyNotksTheme

val colors = listOf(
    Color(0xFFEF9A9A),
    Color(0xFFF48FB1),
    Color(0xFF80CBC4),
    Color(0xFFA5D6A7),
    Color(0xFFFFCC80),
    Color(0xFFFFAB91),
    Color(0xFF81D4FA),
    Color(0xFFCE93D8),
    Color(0xFFB39DDB)
)

@Composable
fun ColorPicker(
    colors: List<Color>,
    onColorSelected: (Color) -> Unit,
    modifier: Modifier = Modifier
) {
    var colorPickerOpen by rememberSaveable { mutableStateOf(false) }
    var currentlySelected by rememberSaveable(saver = colourSaver()) {
        mutableStateOf(colors[0])
    }

    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clickable {
                colorPickerOpen = true
            }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.twotone_palette_24),
            contentDescription = stringResource(id = R.string.color_picker)
        )
    }

    if (colorPickerOpen) {
        ColorDialog(
            colorList = colors,
            onDismiss = { colorPickerOpen = false },
            currentlySelected = currentlySelected,
            onColorSelected = {
                currentlySelected = it
                onColorSelected(it)
            }
        )
    }
}

fun colourSaver() = Saver<MutableState<Color>, String>(
    save = { state -> state.value.toHexString()},
    restore = { value -> mutableStateOf(    value.toColor()) }
)

fun Color.toHexString(): String{
    return String.format(
        "#%02x%02x%02x%02x", (this.alpha * 255).toInt(), (this.red * 255).toInt(),
        (this.green * 255).toInt(), (this.blue * 255).toInt()
    )
}

fun String.toColor(): Color {
    return Color(android.graphics.Color.parseColor(this))
}


@Composable
fun ColorDialog(
    colorList: List<Color>,
    onDismiss: () -> Unit,
    currentlySelected: Color,
    onColorSelected: (Color) -> Unit // when the save button is clicked
) {
    val gridState = rememberLazyGridState()

    AlertDialog(
        shape = RoundedCornerShape(20.dp),
        containerColor = MaterialTheme.colorScheme.background,
        titleContentColor = MaterialTheme.colorScheme.outline,
        onDismissRequest = onDismiss,
        text = {
               LazyVerticalGrid(
                   columns = GridCells.Fixed(3),
                   state = gridState
               ) {
                   items(colorList) { color ->
                       var borderWidth = 0.dp
                       if(currentlySelected == color) {
                           borderWidth = 2.dp
                       }

                       Canvas(
                           modifier = Modifier
                               .padding(16.dp)
                               .clip(RoundedCornerShape(20.dp))
                               .border(
                                   borderWidth,
                                   MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f),
                                   RoundedCornerShape(20.dp)
                               )
                               .background(color)
                               .requiredSize(70.dp)
                               .clickable {
                                   onColorSelected(color)
                                   onDismiss()
                               }
                               .testTag(color.toHexString())
                       ) {}
                   }
               }
        },
        confirmButton = {}
    )
}

@Preview (showBackground = true)
@Composable
fun ColorPickerPreview() {
    MyNotksTheme {
        ColorPicker(colors = colors, onColorSelected = {})
    }
}

@Preview
@Composable
fun ColorDialogPreview() {
    MyNotksTheme {
        ColorDialog(
            colorList = colors,
            onDismiss = { },
            currentlySelected = colors[3],
            onColorSelected = {}
        )
    }
    
}