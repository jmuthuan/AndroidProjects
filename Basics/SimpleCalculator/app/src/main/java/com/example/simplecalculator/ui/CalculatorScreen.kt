package com.example.simplecalculator.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme

@Composable
fun CalculatorScreen(
    modifier: Modifier = Modifier,
    calculatorViewModel: CalculatorViewModel = viewModel(),
    settingsViewModel: SettingsViewModel = viewModel()
    ) {
    val calculatorUiState by calculatorViewModel.uiState.collectAsState()
    val settingsUiState by settingsViewModel.uiState.collectAsState()

    //dialog open/close is pure transient UI state, deliberately not stored in
    //CalculatorUiState or SettingsUiState; the app is portrait-locked so there is
    //no rotation-loss concern
    var isSettingsVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .skeuomorphicChassisBody()
            .padding(start = 16.dp, end = 16.dp, top = 64.dp)
    ) {
        ChassisPanel(modifier = Modifier
            .fillMaxSize()
            .align(Alignment.BottomCenter)
        ) {
            ChassisBrandPlate()
            Spacer(modifier = Modifier
                .height(8.dp)
            )
            InputDisplayComponent(
                result = calculatorUiState.result,
                operation = calculatorUiState.currentOperation,
                fontSizeState = calculatorUiState.currentOperationFontSize,
                autoResize = { calculatorViewModel.resizeCurrentResultFontSize() },
                hasMemory = calculatorUiState.memoryValue != 0.0
            )
            Spacer(modifier = Modifier
                .height(32.dp)
            )
            InputButtonsComponent(
                calculatorViewModel = calculatorViewModel,
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .fillMaxWidth(),
                soundEnabled = settingsUiState.soundEnabled,
                vibrationEnabled = settingsUiState.vibrationEnabled,
                onSettingsClick = { isSettingsVisible = true }
            )
        }

        if (calculatorUiState.isHistoryVisible) {
            HistoryDialog(
                uiState = calculatorUiState,
                onEntrySelected = { calculatorViewModel.selectHistoryEntry(it) },
                onClearRequested = { calculatorViewModel.clearHistory() },
                onDismiss = { calculatorViewModel.closeHistory() }
            )
        }

        if (isSettingsVisible) {
            SettingsDialog(
                uiState = settingsUiState,
                onSoundToggle = settingsViewModel::setSoundEnabled,
                onVibrationToggle = settingsViewModel::setVibrationEnabled,
                onDismiss = { isSettingsVisible = false }
            )
        }
    }


}



@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    SimpleCalculatorTheme {
        CalculatorScreen()
    }
}