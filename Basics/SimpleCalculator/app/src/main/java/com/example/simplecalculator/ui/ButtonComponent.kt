package com.example.simplecalculator.ui

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simplecalculator.R
import com.example.simplecalculator.ui.theme.ButtonShadowColorBottom
import com.example.simplecalculator.ui.theme.ButtonShadowColorTop
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme
import com.example.simplecalculator.ui.theme.handjetFontFamily

@Composable
fun ButtonComponent(
    modifier: Modifier = Modifier,
    color: Color,
    symbol: String? = null,
    image: Pair<Painter, String>? = null,
    onClick: () -> Unit
) {
    val mutableInteractionSource = remember {
        MutableInteractionSource()
    }

    val pressed = mutableInteractionSource.collectIsPressedAsState()

    val size: Float by animateFloatAsState(
        targetValue = if(pressed.value) 1.0f else 0.8f,
        label = "box size",
        animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium
        )
    )


    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(color)
            .clickable(
                interactionSource = mutableInteractionSource,
                indication = null
            ) {
                onClick()
            }
            .size(height = 64.dp, width = 64.dp)
            .then(modifier),
        contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
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
                        .fillMaxSize(size),
                    contentAlignment = Alignment.Center
                ) {
                    if (symbol == null) {
                        Image(
                            painter =
                                image?.first ?: painterResource(id = R.drawable.baseline_image_24),
                            contentDescription = image?.second,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text(
                            text = symbol,
                            fontFamily = handjetFontFamily,
                            color = Color.White,
                            fontSize = 28.sp
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