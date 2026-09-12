package com.example.simplecalculator.ui

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class HistoryEntry(
    val expression: String,
    val result: String
)

data class CalculatorUiState(
    var currentOperation: String = "",
    val result: String = "",
    val currentOperationFontSize: TextUnit = 48.sp,
    val history: List<HistoryEntry> = emptyList(),
    val isHistoryVisible: Boolean = false
)