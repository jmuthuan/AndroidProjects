package com.example.simplecalculator.ui

data class CalculatorUiState(
    var currentOperation: String = "",
    val result: String = ""
)