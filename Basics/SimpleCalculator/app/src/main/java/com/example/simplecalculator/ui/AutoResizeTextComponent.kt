package com.example.simplecalculator.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun AutoResizeTextComponent(
    text: String,
    fontSize: TextUnit,
    fontFamily: FontFamily,
    fontWeight: FontWeight,
    modifier: Modifier = Modifier,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Visible,
    color: Color = Color.LightGray,
    autoResize: () -> Unit,
    style: TextStyle = MaterialTheme.typography.headlineLarge.merge(
        TextStyle(textAlign = TextAlign.End)
    )
) {

    var shouldDraw by remember {
        mutableStateOf(false)
    }

    val minTextSize = 24.sp
    var marqueeIteration = 0

    Text(
        text = text,
        fontFamily = fontFamily,
        fontWeight = fontWeight,
        fontSize = fontSize,//        fontSize = resizeTextSize,
        maxLines = maxLines,
        color = color,
        style = style,
        softWrap = false,
        onTextLayout = { result ->
            if(result.didOverflowWidth) {
                if(fontSize > minTextSize) {
                    autoResize()
                } else {
                    marqueeIteration = Int.MAX_VALUE
                }
            } else {
                shouldDraw = true
            }
        },
        modifier = modifier
            .drawWithContent {
                if (shouldDraw) {
                    drawContent()
                }
            }
            .then(
                if (fontSize <= minTextSize) {
                    Modifier
                        .horizontalScroll(
                            state = rememberScrollState(),
                            reverseScrolling = true
                        )
                } else {
                    Modifier
                }
            )
    )
}