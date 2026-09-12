package com.example.simplecalculator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.simplecalculator.R
import com.example.simplecalculator.ui.theme.ChassisBezelHighlight
import com.example.simplecalculator.ui.theme.ChassisBezelShadow
import com.example.simplecalculator.ui.theme.ChassisPanelColorDark
import com.example.simplecalculator.ui.theme.ChassisPanelColorLight
import com.example.simplecalculator.ui.theme.EqualButtonColor
import com.example.simplecalculator.ui.theme.OperationButtonsColor
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme
import com.example.simplecalculator.ui.theme.sevenSegmentFont

/**
 * Session-only calculation history, presented as a vintage chassis-styled panel
 * (same [ChassisPanelColorLight]/[ChassisPanelColorDark] gradient + emboss [Modifier.shadow]
 * + [MaterialTheme.shapes.extraLarge] primitives as [ChassisPanel]) inside a plain
 * [Dialog], so it reads as part of the calculator's own vintage identity rather than a
 * generic Material dialog. The secondary clear-confirmation prompt intentionally stays a
 * stock Material3 [AlertDialog] per the architecture decision.
 */
@Composable
fun HistoryDialog(
    uiState: CalculatorUiState,
    onEntrySelected: (HistoryEntry) -> Unit,
    onClearRequested: () -> Unit,
    onDismiss: () -> Unit
) {
    var showClearConfirmation by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    color = ChassisBezelShadow,
                    offsetX = 6.dp,
                    offsetY = 6.dp,
                    blurRadius = 16.dp
                )
                .shadow(
                    color = ChassisBezelHighlight,
                    offsetX = (-6).dp,
                    offsetY = (-6).dp,
                    blurRadius = 16.dp
                )
                .clip(MaterialTheme.shapes.extraLarge)
                .background(Brush.verticalGradient(listOf(ChassisPanelColorLight, ChassisPanelColorDark)))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.history_title),
                    fontFamily = sevenSegmentFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )

                ButtonComponent(
                    color = OperationButtonsColor,
                    modifier = Modifier.size(40.dp),
                    symbol = stringResource(id = R.string.history_close),
                    onClick = onDismiss
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp, max = 320.dp)
                    .padding(vertical = 8.dp)
            ) {
                if (uiState.history.isEmpty()) {
                    Text(
                        text = stringResource(id = R.string.history_empty),
                        fontFamily = sevenSegmentFont,
                        color = Color.LightGray,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp)
                    )
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(uiState.history) { entry ->
                            HistoryEntryRow(entry = entry, onClick = { onEntrySelected(entry) })
                        }
                    }
                }
            }

            ButtonComponent(
                color = EqualButtonColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 48.dp),
                symbol = stringResource(id = R.string.history_clear),
                onClick = { showClearConfirmation = true }
            )
        }
    }

    if (showClearConfirmation) {
        AlertDialog(
            onDismissRequest = { showClearConfirmation = false },
            title = { Text(text = stringResource(id = R.string.history_clear_confirm_title)) },
            text = { Text(text = stringResource(id = R.string.history_clear_confirm_message)) },
            confirmButton = {
                TextButton(onClick = {
                    showClearConfirmation = false
                    onClearRequested()
                }) {
                    Text(text = stringResource(id = R.string.history_clear_confirm_confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showClearConfirmation = false
                }) {
                    Text(text = stringResource(id = R.string.history_clear_confirm_cancel))
                }
            }
        )
    }
}

@Composable
private fun HistoryEntryRow(entry: HistoryEntry, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp, horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = entry.expression,
            fontFamily = sevenSegmentFont,
            color = Color.LightGray,
            fontSize = 16.sp,
            maxLines = 1
        )
        Text(
            text = entry.result,
            fontFamily = sevenSegmentFont,
            fontWeight = FontWeight.Bold,
            color = Color.Green,
            fontSize = 18.sp,
            maxLines = 1
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryDialogPreview() {
    SimpleCalculatorTheme {
        HistoryDialog(
            uiState = CalculatorUiState(
                history = listOf(
                    HistoryEntry("101+49", "150.00"),
                    HistoryEntry("20x6", "120.00")
                ),
                isHistoryVisible = true
            ),
            onEntrySelected = {},
            onClearRequested = {},
            onDismiss = {}
        )
    }
}
