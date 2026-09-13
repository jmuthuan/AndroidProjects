package com.example.simplecalculator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.simplecalculator.ui.theme.OperationButtonsColor
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme
import com.example.simplecalculator.ui.theme.sevenSegmentFont

/**
 * Sound/Vibration feedback preferences, presented with the same vintage chassis-styled
 * panel treatment as [HistoryDialog] (double emboss [Modifier.shadow] + gradient +
 * [MaterialTheme.shapes.extraLarge] clip inside a plain [Dialog]) rather than a stock
 * Material [androidx.compose.material3.AlertDialog] shell.
 */
@Composable
fun SettingsDialog(
    uiState: SettingsUiState,
    onSoundToggle: (Boolean) -> Unit,
    onVibrationToggle: (Boolean) -> Unit,
    onDismiss: () -> Unit
) {
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
                    text = stringResource(id = R.string.settings_title),
                    fontFamily = sevenSegmentFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )

                ButtonComponent(
                    color = OperationButtonsColor,
                    modifier = Modifier.size(40.dp),
                    symbol = stringResource(id = R.string.settings_close),
                    onClick = onDismiss
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                SettingsToggleRow(
                    label = stringResource(id = R.string.settings_sound_label),
                    checked = uiState.soundEnabled,
                    onCheckedChange = onSoundToggle
                )
                SettingsToggleRow(
                    label = stringResource(id = R.string.settings_vibration_label),
                    checked = uiState.vibrationEnabled,
                    onCheckedChange = onVibrationToggle
                )
            }
        }
    }
}

@Composable
private fun SettingsToggleRow(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontFamily = sevenSegmentFont,
            color = Color.LightGray,
            fontSize = 16.sp
        )
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsDialogPreview() {
    SimpleCalculatorTheme {
        SettingsDialog(
            uiState = SettingsUiState(soundEnabled = true, vibrationEnabled = false),
            onSoundToggle = {},
            onVibrationToggle = {},
            onDismiss = {}
        )
    }
}
