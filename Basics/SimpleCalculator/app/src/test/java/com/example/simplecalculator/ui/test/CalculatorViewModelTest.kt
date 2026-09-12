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

    //--- toggleSign() tests ---

    @Test
    fun calculatorViewModel_toggleSignAtStartOfExpression_insertsAndRemovesLeadingMinus() {
        viewModel.toggleSign()
        assertEquals("-", viewModel.uiState.value.currentOperation)

        viewModel.enterNumber('5')
        assertEquals("-5", viewModel.uiState.value.currentOperation)

        viewModel.calculateResult()
        assertEquals("-5.00", viewModel.uiState.value.result)
    }

    @Test
    fun calculatorViewModel_toggleSignAtStartOfExpression_isReversible() {
        viewModel.toggleSign()
        assertEquals("-", viewModel.uiState.value.currentOperation)

        viewModel.toggleSign()
        assertEquals("", viewModel.uiState.value.currentOperation)
    }

    @Test
    fun calculatorViewModel_toggleSignMidNumber_appliesToTrailingNumber() {
        viewModel.enterNumber('1')
        viewModel.enterNumber('2')
        viewModel.toggleSign()

        assertEquals("-12", viewModel.uiState.value.currentOperation)

        viewModel.enterNumber('3')
        assertEquals("-123", viewModel.uiState.value.currentOperation)

        viewModel.calculateResult()
        assertEquals("-123.00", viewModel.uiState.value.result)

        //remove the '3' just typed, back to the "-12" state, then toggle again:
        //restores the pre-toggle "12" from the very first toggle above
        viewModel.backspace()
        assertEquals("-12", viewModel.uiState.value.currentOperation)

        viewModel.toggleSign()
        assertEquals("12", viewModel.uiState.value.currentOperation)
    }

    @Test
    fun calculatorViewModel_toggleSignAfterPlus_flipsToMinus_andIsReversible() {
        viewModel.enterNumber('5')
        viewModel.updateOperation('+')
        viewModel.toggleSign()

        assertEquals("5-", viewModel.uiState.value.currentOperation)

        viewModel.enterNumber('3')
        assertEquals("5-3", viewModel.uiState.value.currentOperation)

        viewModel.calculateResult()
        assertEquals("2.00", viewModel.uiState.value.result)

        viewModel.backspace()
        viewModel.toggleSign()
        assertEquals("5+", viewModel.uiState.value.currentOperation)
    }

    @Test
    fun calculatorViewModel_toggleSignAfterMinus_flipsToPlus_andIsReversible() {
        viewModel.enterNumber('5')
        viewModel.updateOperation('-')
        viewModel.toggleSign()

        assertEquals("5+", viewModel.uiState.value.currentOperation)

        viewModel.enterNumber('3')
        assertEquals("5+3", viewModel.uiState.value.currentOperation)

        viewModel.calculateResult()
        assertEquals("8.00", viewModel.uiState.value.result)

        viewModel.backspace()
        viewModel.toggleSign()
        assertEquals("5-", viewModel.uiState.value.currentOperation)
    }

    @Test
    fun calculatorViewModel_toggleSignAfterMultiply_insertsMinus_andIsReversible() {
        viewModel.enterNumber('5')
        viewModel.updateOperation('x')
        viewModel.toggleSign()

        assertEquals("5x-", viewModel.uiState.value.currentOperation)

        viewModel.enterNumber('3')
        assertEquals("5x-3", viewModel.uiState.value.currentOperation)

        viewModel.calculateResult()
        assertEquals("-15.00", viewModel.uiState.value.result)

        viewModel.backspace()
        viewModel.toggleSign()
        assertEquals("5x", viewModel.uiState.value.currentOperation)
    }

    @Test
    fun calculatorViewModel_toggleSignAfterDivide_insertsMinus_andIsReversible() {
        viewModel.enterNumber('5')
        viewModel.updateOperation('/')
        viewModel.toggleSign()

        assertEquals("5/-", viewModel.uiState.value.currentOperation)

        viewModel.enterNumber('3')
        assertEquals("5/-3", viewModel.uiState.value.currentOperation)

        viewModel.calculateResult()
        assertEquals("-1.67", viewModel.uiState.value.result)

        viewModel.backspace()
        viewModel.toggleSign()
        assertEquals("5/", viewModel.uiState.value.currentOperation)
    }

    @Test
    fun calculatorViewModel_toggleSignAfterOpenParenthesis_insertsLeadingMinus_andIsReversible() {
        viewModel.parenthesis()
        viewModel.toggleSign()

        assertEquals("(-", viewModel.uiState.value.currentOperation)

        viewModel.enterNumber('5')
        viewModel.updateOperation('+')
        viewModel.enterNumber('3')
        viewModel.parenthesis()

        assertEquals("(-5+3)", viewModel.uiState.value.currentOperation)

        viewModel.calculateResult()
        assertEquals("-2.00", viewModel.uiState.value.result)
    }

    @Test
    fun calculatorViewModel_toggleSignAfterPercentage_isNoOp() {
        viewModel.enterNumber('5')
        viewModel.enterNumber('0')
        viewModel.updateOperation('%')

        assertEquals("50%", viewModel.uiState.value.currentOperation)

        viewModel.toggleSign()

        assertEquals("50%", viewModel.uiState.value.currentOperation)
    }

    @Test
    fun calculatorViewModel_toggleSignAfterCloseParenthesis_isNoOp() {
        viewModel.parenthesis()
        viewModel.enterNumber('5')
        viewModel.updateOperation('+')
        viewModel.enterNumber('3')
        viewModel.parenthesis()

        assertEquals("(5+3)", viewModel.uiState.value.currentOperation)

        viewModel.toggleSign()

        assertEquals("(5+3)", viewModel.uiState.value.currentOperation)
    }

    //--- simplify() engine regression checks for Gap A / Gap B (isolated from toggleSign UI plumbing) ---

    @Test
    fun calculatorViewModel_multiplyByNegativeOperand_resultOperation() {
        val currentCalculatorUiState = viewModel.uiState.value
        currentCalculatorUiState.currentOperation = "5x-3"

        viewModel.calculateResult()

        assertEquals("-15.00", viewModel.uiState.value.result)
    }

    @Test
    fun calculatorViewModel_divideByNegativeOperand_resultOperation() {
        val currentCalculatorUiState = viewModel.uiState.value
        currentCalculatorUiState.currentOperation = "5/-3"

        viewModel.calculateResult()

        assertEquals("-1.67", viewModel.uiState.value.result)
    }

    @Test
    fun calculatorViewModel_leadingNegativeNumberAddition_resultOperation() {
        val currentCalculatorUiState = viewModel.uiState.value
        currentCalculatorUiState.currentOperation = "-5+3"

        viewModel.calculateResult()

        assertEquals("-2.00", viewModel.uiState.value.result)
    }

}
