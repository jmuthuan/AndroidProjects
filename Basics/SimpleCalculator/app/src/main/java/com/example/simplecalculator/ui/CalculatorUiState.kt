package com.example.simplecalculator.ui

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class CalculatorUiState(
    var currentOperation: String = "",
    val result: String = "",
    val currentOperationFontSize: TextUnit = 48.sp
)