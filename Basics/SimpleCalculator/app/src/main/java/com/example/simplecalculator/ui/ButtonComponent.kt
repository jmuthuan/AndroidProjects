package com.example.simplecalculator.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simplecalculator.R
import com.example.simplecalculator.ui.theme.ButtonShadowColorBottom
import com.example.simplecalculator.ui.theme.ButtonShadowColorTop
import com.example.simplecalculator.ui.theme.DisplayShadowColorBottom
import com.example.simplecalculator.ui.theme.DisplayShadowColorTop
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme
import com.example.simplecalculator.ui.theme.handjetFontFamily

@Composable
fun ButtonComponent(
    modifier: Modifier = Modifier,
    color: Color,
    symbol: String? = null,
    image: Unit? = null,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(color)
            .clickable { onClick() }
            .size(height = 64.dp, width = 64.dp)
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize(fraction = 0.8f)
                .shadow(
                    color = ButtonShadowColorTop,
                    offsetX = (-4).dp,
                    offsetY = (-4).dp,
                    blurRadius = 8.dp
                )
                .shadow(
                    color = ButtonShadowColorBottom,
                    offsetX = 4.dp,
                    offsetY = 4.dp,
                    blurRadius = 8.dp
                )
                .clip(MaterialTheme.shapes.medium)
                .background(color)
        ) {
            if (symbol == null) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_backspace_24),
                    contentDescription = "erase last operation",
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text(
                    text = symbol,
                    fontFamily = handjetFontFamily,
                    color = Color.White,
                    fontSize = 32.sp,
//                    fontWeight = FontWeight.Normal
                )
            }

        }
    }
}

@Preview (showBackground = true)
@Composable
fun ButtonComponentPreview() {
    SimpleCalculatorTheme {
        ButtonComponent(
            modifier = Modifier.size(100.dp),
            color = Color(150,0,0),
            symbol = "1",
            onClick = {}
        )
    }
}