package com.example.simplecalculator.ui.test

import com.example.simplecalculator.ui.CalculatorViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class CalculatorViewModelTest {
    private val viewModel = CalculatorViewModel()

    //fun thingUnderTest_TriggerOfTest_ResultOfTest()
    @Test
    fun calculatorViewModel_simpleAddOperation_operationResult() {
        //operation = 101+49

        val currentCalculatorUiState = viewModel.uiState.value
        currentCalculatorUiState.currentOperation = "101+49"

        viewModel.calculateResult()

        assertEquals("150.00", viewModel.uiState.value.result)
    }

    @Test
    fun calculatorViewModel_simpleMultiplyOperation_operationResult() {
        val currentCalculatorUiState = viewModel.uiState.value
        currentCalculatorUiState.currentOperation = "20x6"

        viewModel.calculateResult()

        assertEquals("120.00", viewModel.uiState.value.result)
    }

    @Test
    fun calculatorViewModel_simpleSubtractionOperation_operationResult() {
        val currentCalculatorUiState = viewModel.uiState.value
        currentCalculatorUiState.currentOperation = "117-99"

        viewModel.calculateResult()

        assertEquals("18.00", viewModel.uiState.value.result)

    }

    @Test
    fun calculatorViewModel_simpleDivisionOperation_operationResult() {
        val currentCalculatorUiState = viewModel.uiState.value
        currentCalculatorUiState.currentOperation = "96/24"

        viewModel.calculateResult()

        assertEquals("4.00", viewModel.uiState.value.result)
    }

    @Test
    fun calculatorViewModel_divisionByZero_ErrorDisplay() {
        val currentCalculatorUiState = viewModel.uiState.value
        currentCalculatorUiState.currentOperation = "15/0"

        viewModel.calculateResult()

        assertEquals("Cannot be divided by 0", viewModel.uiState.value.result)
    }

    @Test
    fun calculatorViewModel_percentageOperation_resultOperation() {
        val currentCalculatorUiState = viewModel.uiState.value
        currentCalculatorUiState.currentOperation = "10+2x50%"

        viewModel.calculateResult()

        assertEquals("11.00", viewModel.uiState.value.result)

        val currentCalculatorUiState2 = viewModel.uiState.value
        currentCalculatorUiState2.currentOperation = "10+35%"

        viewModel.calculateResult()

        assertEquals("13.50", viewModel.uiState.value.result)
    }

}
