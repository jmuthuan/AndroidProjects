package com.example.mynotks.ui.lists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mynotks.data.Notks
import com.example.mynotks.data.TypesNotks
import com.example.mynotks.ui.theme.MyNotksTheme

@Composable
fun ListsShort(
    notks: Notks,
    onClickList: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 32.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = modifier
            .fillMaxWidth()
//            .height(150.dp)
            .clickable {
                onClickList(notks.id)
            }
    ) {
        Text(
            text = notks.title,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        repeat(2) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = false,
                    onCheckedChange = null
                )
                Text(text = "Task ...")
            }
        }
    }


}

@Preview
@Composable
fun ListsShortPreview() {
    MyNotksTheme {
        ListsShort(
            Notks(0, TypesNotks.LIST, title = "List First Test"),
            {})
    }
}