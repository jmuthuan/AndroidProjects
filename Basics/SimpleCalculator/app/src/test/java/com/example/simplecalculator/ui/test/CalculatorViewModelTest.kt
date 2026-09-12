package com.example.simplecalculator.ui.test

import com.example.simplecalculator.ui.CalculatorViewModel
import com.example.simplecalculator.ui.HistoryEntry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
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

    //--- Calculation history log tests ---

    @Test
    fun calculatorViewModel_successfulCalculation_addsHistoryEntry() {
        viewModel.uiState.value.currentOperation = "101+49"

        viewModel.calculateResult()

        val history = viewModel.uiState.value.history
        assertEquals(1, history.size)
        assertEquals(HistoryEntry("101+49", "150.00"), history[0])
    }

    @Test
    fun calculatorViewModel_syntaxErrorResult_doesNotAddHistoryEntry() {
        viewModel.uiState.value.currentOperation = "+"

        viewModel.calculateResult()

        assertEquals("Syntax error", viewModel.uiState.value.result)
        assertTrue(viewModel.uiState.value.history.isEmpty())
    }

    @Test
    fun calculatorViewModel_divisionByZeroResult_doesNotAddHistoryEntry() {
        viewModel.uiState.value.currentOperation = "15/0"

        viewModel.calculateResult()

        assertEquals("Cannot be divided by 0", viewModel.uiState.value.result)
        assertTrue(viewModel.uiState.value.history.isEmpty())
    }

    @Test
    fun calculatorViewModel_newEntries_arePrependedMostRecentFirst() {
        viewModel.uiState.value.currentOperation = "1+1"
        viewModel.calculateResult()

        viewModel.uiState.value.currentOperation = "2+2"
        viewModel.calculateResult()

        val history = viewModel.uiState.value.history
        assertEquals(HistoryEntry("2+2", "4.00"), history[0])
        assertEquals(HistoryEntry("1+1", "2.00"), history[1])
    }

    @Test
    fun calculatorViewModel_moreThanMaxEntries_dropsOldestAndCapsAt20() {
        for (i in 1..21) {
            viewModel.uiState.value.currentOperation = "$i+0"
            viewModel.calculateResult()
        }

        val history = viewModel.uiState.value.history
        assertEquals(20, history.size)
        //most recent (21+0) is first, oldest kept is (2+0); (1+0) was dropped
        assertEquals(HistoryEntry("21+0", "21.00"), history[0])
        assertEquals(HistoryEntry("2+0", "2.00"), history[19])
        assertFalse(history.any { it.expression == "1+0" })
    }

    @Test
    fun calculatorViewModel_selectHistoryEntry_loadsExpressionAndResult() {
        viewModel.uiState.value.currentOperation = "20x6"
        viewModel.calculateResult()
        val entry = viewModel.uiState.value.history[0]

        viewModel.backspace() //perturb state before recall to prove selection overwrites it
        viewModel.selectHistoryEntry(entry)

        assertEquals("20x6", viewModel.uiState.value.currentOperation)
        assertEquals("120.00", viewModel.uiState.value.result)
        assertFalse(viewModel.uiState.value.isHistoryVisible)
    }

    @Test
    fun calculatorViewModel_selectHistoryEntryWithParenthesis_thenBackspaceAndParenthesis_doesNotThrow() {
        //built through the real button-driven API (parenthesis()/enterNumber()/updateOperation())
        //so mapParenthesis/parenthesisCount are populated exactly like real user input would,
        //rather than bypassing that bookkeeping via direct string assignment
        viewModel.parenthesis()
        viewModel.enterNumber('4')
        viewModel.updateOperation('+')
        viewModel.enterNumber('6')
        viewModel.parenthesis()
        viewModel.calculateResult()

        val entry = viewModel.uiState.value.history[0]
        assertEquals("(4+6)", entry.expression)

        viewModel.selectHistoryEntry(entry)
        assertEquals("(4+6)", viewModel.uiState.value.currentOperation)

        //regression guard for rebuildParenthesisState(): continued editing of a
        //recalled parenthesis-containing expression must not throw NoSuchElementException
        viewModel.backspace()
        assertEquals("(4+6", viewModel.uiState.value.currentOperation)

        viewModel.parenthesis()
        assertEquals("(4+6)", viewModel.uiState.value.currentOperation)
    }

    @Test
    fun calculatorViewModel_clearHistory_emptiesHistoryOnly() {
        viewModel.uiState.value.currentOperation = "101+49"
        viewModel.calculateResult()

        viewModel.clearHistory()

        assertTrue(viewModel.uiState.value.history.isEmpty())
        assertEquals("101+49", viewModel.uiState.value.currentOperation)
        assertEquals("150.00", viewModel.uiState.value.result)
    }

    @Test
    fun calculatorViewModel_clearDisplay_doesNotClearHistory() {
        viewModel.uiState.value.currentOperation = "101+49"
        viewModel.calculateResult()

        viewModel.clearDisplay()

        assertEquals("", viewModel.uiState.value.currentOperation)
        assertEquals(1, viewModel.uiState.value.history.size)
        assertEquals(HistoryEntry("101+49", "150.00"), viewModel.uiState.value.history[0])
    }

    @Test
    fun calculatorViewModel_openAndCloseHistory_toggleIsHistoryVisible() {
        assertFalse(viewModel.uiState.value.isHistoryVisible)

        viewModel.openHistory()
        assertTrue(viewModel.uiState.value.isHistoryVisible)

        viewModel.closeHistory()
        assertFalse(viewModel.uiState.value.isHistoryVisible)
    }

}
