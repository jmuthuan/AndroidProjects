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

        var currentCalculatorUiState = viewModel.uiState.value
        currentCalculatorUiState.currentOperation = "101+49"

        viewModel.calculateResult()

        assertEquals("150.0", viewModel.uiState.value.result)
    }

    @Test
    fun calculatorViewModel_simpleMultiplyOperation_operationResult() {
        var currentCalculatorUiState = viewModel.uiState.value
        currentCalculatorUiState.currentOperation = "20x6"

        viewModel.calculateResult()

        assertEquals("120.0", viewModel.uiState.value.result)
    }

    @Test
    fun calculatorViewModel_simpleSubtractionOperation_operationResult() {
        var currentCalculatorUiState = viewModel.uiState.value
        currentCalculatorUiState.currentOperation = "117-99"

        viewModel.calculateResult()

        assertEquals("18.0", viewModel.uiState.value.result)

    }

    @Test
    fun calculatorViewModel_simpleDivisionOperation_operationResult() {
        var currentCalculatorUiState = viewModel.uiState.value
        currentCalculatorUiState.currentOperation = "96/24"

        viewModel.calculateResult()

        assertEquals("4.0", viewModel.uiState.value.result)
    }

    @Test
    fun calculatorViewModel_divisionByZero_ErrorDisplay() {
        var currentCalculatorUiState = viewModel.uiState.value
        currentCalculatorUiState.currentOperation = "15/0"

        viewModel.calculateResult()

        assertEquals("Cannot be divided by 0", viewModel.uiState.value.result)
    }





    @Test
    fun calculatorViewModel_decimalNumbersOperation_operationWithDecimalsResult() {
        var currentCalculatorUiState = viewModel.uiState.value
        currentCalculatorUiState.currentOperation = "((2.5+0.8)x(1.2-0.3))"

        viewModel.calculateResult()

        assertEquals("2.97", viewModel.uiState.value.result)

    }

    @Test
    fun calculatorViewModel_percentageOperation_resultOperation() {
        var currentCalculatorUiState = viewModel.uiState.value
        currentCalculatorUiState.currentOperation = "10+2x50%"

        viewModel.calculateResult()

        assertEquals("11.0", viewModel.uiState.value.result)

        currentCalculatorUiState.currentOperation = "10+35%"

        viewModel.calculateResult()

        assertEquals("13.5", viewModel.uiState.value.result)
    }

    @Test
    fun calculatorViewModel_combinedComplexOperation_operationResult() {
        var currentCalculatorUiState = viewModel.uiState.value
        currentCalculatorUiState.currentOperation = "((((12.5+8.3)*(4+6)+(45-5)%)x2.35"

        viewModel.calculateResult()

        assertEquals("489.74", viewModel.uiState.value.result)

    }

}
