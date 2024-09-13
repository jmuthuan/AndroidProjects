package com.example.simplecalculator.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simplecalculator.ui.theme.DisplayShadowColorBottom
import com.example.simplecalculator.ui.theme.DisplayShadowColorTop
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme
import com.example.simplecalculator.ui.theme.sevenSegmentFont

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InputDisplayComponent(result: String, operation: String, modifier: Modifier = Modifier) {
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
//            .height(210.dp)
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
//                    .horizontalScroll(rememberScrollState())
                    .basicMarquee(
                        iterations = Int.MAX_VALUE,
                        velocity = 100.dp,
                        initialDelayMillis = 250
                    )
            )

            Text(
                text = operation,
                fontFamily = sevenSegmentFont,
                fontWeight = FontWeight.Black,
                fontSize = 36.sp,
                maxLines = 1,
                overflow = TextOverflow.Visible,
                color = Color.LightGray,
                style = MaterialTheme.typography.headlineLarge.merge(
                    TextStyle(textAlign = TextAlign.End)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp)
                    .horizontalScroll(rememberScrollState())
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
            InputDisplayComponent(result = "100", operation = "15+85")
        }
    }
}