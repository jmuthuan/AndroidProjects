package com.example.simplecalculator.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simplecalculator.ui.theme.DisplayShadowColorBottom
import com.example.simplecalculator.ui.theme.DisplayShadowColorTop
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme
import com.example.simplecalculator.ui.theme.sevenSegmentFont

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InputDisplayComponent(
    result: String,
    operation: String,
    fontSizeState: TextUnit,
    autoResize: () -> Unit,
    modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val screenHeightDp = configuration.screenHeightDp.dp
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                color = DisplayShadowColorTop,
                offsetX = (-6).dp,
                offsetY = (-6).dp,
                blurRadius = 15.dp
            )
            .shadow(
                color = DisplayShadowColorBottom,
                offsetX = (6).dp,
                offsetY = (6).dp,
                blurRadius = 15.dp
            )
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.onBackground)
            .padding(vertical = 16.dp, horizontal = 16.dp)
            .height(screenHeightDp / 6)
    ) {
        Column {
            Text(
                text = result,
                fontFamily = sevenSegmentFont,
                fontWeight = FontWeight.Bold,
                fontSize = 48.sp,
                maxLines = 1,
                overflow = TextOverflow.Visible,
                color = if(result.toDoubleOrNull() != null) Color.Green else Color.Red,
                style = MaterialTheme.typography.headlineLarge.merge(
                    TextStyle(textAlign = TextAlign.End)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .basicMarquee(
                        iterations = Int.MAX_VALUE,
                        velocity = 100.dp,
                        initialDelayMillis = 250
                    )
            )
            AutoResizeTextComponent(
                text = operation,
                fontFamily = sevenSegmentFont,
                fontWeight = FontWeight.Black,
                fontSize = fontSizeState,
                maxLines = 1,
                color = Color.LightGray,
                style = MaterialTheme.typography.headlineLarge.merge(
                    TextStyle(textAlign = TextAlign.End)
                ),
                autoResize = { autoResize() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp)
            )
        }
    }
    
}

@Preview
@Composable
fun InputDisplayComponentPreview() {
    SimpleCalculatorTheme {
        Box(
            modifier = Modifier
            .padding(16.dp)
        ) {
            InputDisplayComponent(
                result = "100",
                operation = "15+85" ,
                fontSizeState = 48.sp,
                autoResize = {}
            )
        }
    }
}