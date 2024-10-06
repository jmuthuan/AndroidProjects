package com.example.mynotks.ui.notes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mynotks.data.Notks


//object NotesShortDestination: NavigationDestination {
//    override val route = "notes_short"
//    override val titleRes = R.string.title_res
//
//}

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
            .clickable { onClickNotks(notks.id) }
    ) {
        Text(
            text = notks.title,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 32.sp
        )
        Text(
            text = "(your notes):  ${notks.id}",
            modifier = Modifier
                .padding(8.dp),
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}
