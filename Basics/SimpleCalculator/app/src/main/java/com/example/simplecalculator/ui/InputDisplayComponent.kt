package com.example.simplecalculator.ui

import androidx.compose.foundation.background
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
            .padding(vertical = 32.dp, horizontal = 16.dp)
            .height(210.dp)
    ) {
        Column {
            Text(
                text = result,
                fontFamily = sevenSegmentFont,
                fontWeight = FontWeight.Bold,
                fontSize = 72.sp,
                maxLines = 1,
                overflow = TextOverflow.Visible,
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge.merge(
                    TextStyle(textAlign = TextAlign.End)
                ),
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Text(
                text = operation,
                fontFamily = sevenSegmentFont,
                fontWeight = FontWeight.Black,
                fontSize = 48.sp,
                maxLines = 1,
                overflow = TextOverflow.Visible,
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge.merge(
                    TextStyle(textAlign = TextAlign.End)
                ),
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 48.dp, bottom = 8.dp)
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
//            .background(Color.Black)
        ) {
            InputDisplayComponent(result = "100", operation = "15+85")
        }
    }
}