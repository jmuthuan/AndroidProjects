package com.example.mynotks.ui.notes

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mynotks.data.Notks
import com.example.mynotks.ui.shadow
import com.example.mynotks.ui.theme.nanumFontfamily
import com.example.mynotks.ui.theme.onBackgroundLight


@Composable
fun NotesShort(
    notks: Notks,
    onClickNotks: (Int) -> Unit,
    backgroundColor: Color = Color.LightGray,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 32.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClickNotks(notks.id) }
            .border(1.dp, MaterialTheme.colorScheme.onBackground, RoundedCornerShape(12.dp))
            .shadow(
                color = backgroundColor,
                offsetX = 4.dp,
                offsetY = 4.dp,
                blurRadius = 8.dp
            )
    ) {
        Text(
            text = notks.title,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 32.sp,
                fontFamily = nanumFontfamily,
                fontWeight = FontWeight.Bold,
                color = onBackgroundLight
            )
        )
        Text(
            text = "(Click to read...)",
            modifier = Modifier
                .padding(8.dp),
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = nanumFontfamily,
                fontWeight = FontWeight.Normal,
                color = onBackgroundLight
            ),
            textAlign = TextAlign.Center
        )
    }
}
