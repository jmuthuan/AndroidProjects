package com.example.mynotks.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mynotks.R
import com.example.mynotks.ui.theme.nanumFontfamily
import com.example.mynotks.ui.theme.onBackgroundLight

@Composable
fun NotksSnackbar(
    message: String = stringResource(id = R.string.snackbar_empty_message),
    containerColor: Color = MaterialTheme.colorScheme.onBackground,
) {
    Snackbar(
        containerColor = containerColor,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .shadow(
                color = containerColor,
                offsetX = 4.dp,
                offsetY = 4.dp,
                blurRadius = 8.dp
            )
    ) {
        Text(
            text = message,
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = nanumFontfamily,
                fontWeight = FontWeight.Normal,
                color = onBackgroundLight
            ),
            color = Color.White
        )
    }
    
}