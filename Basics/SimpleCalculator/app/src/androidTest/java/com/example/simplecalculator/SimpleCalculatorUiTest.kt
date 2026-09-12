package com.example.simplecalculator

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.simplecalculator.ui.CalculatorScreen
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme
import org.junit.Rule
import org.junit.Test

class SimpleCalculatorUiTest {
    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun calculatorUi_parenthesisSolving_operationWithParenthesisResult() {
        composeTestRule.setContent {
            SimpleCalculatorTheme {
                CalculatorScreen()
            }
        }

        //operation: "((12+8)*(4+6)+(45-5))"
        repeat(2){ composeTestRule.onNodeWithText("( )").performClick() }
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("2").performClick()
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("8").performClick()
        composeTestRule.onNodeWithText("( )").performClick()
        composeTestRule.onNodeWithText("x").performClick()
        composeTestRule.onNodeWithText("( )").performClick()
        composeTestRule.onNodeWithText("4").performClick()
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("6").performClick()
        composeTestRule.onNodeWithText("( )").performClick()
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("( )").performClick()
        composeTestRule.onNodeWithText("4").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("-").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        repeat(2){ composeTestRule.onNodeWithText("( )").performClick() }

        composeTestRule.onNodeWithText("=").performClick()


        composeTestRule.onNodeWithText("240.00").assertExists("No node with this result")
    }


    @Test
    fun calculatorUi_parenthesisLeftOpenSolving_operationWithOpenParenthesisResult() {
        composeTestRule.setContent {
            SimpleCalculatorTheme {
                CalculatorScreen()
            }
        }

        //operation: "((12+8)*(4+6)+(45-5"
        repeat(2){ composeTestRule.onNodeWithText("( )").performClick() }
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("2").performClick()
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("8").performClick()
        composeTestRule.onNodeWithText("( )").performClick()
        composeTestRule.onNodeWithText("x").performClick()
        composeTestRule.onNodeWithText("( )").performClick()
        composeTestRule.onNodeWithText("4").performClick()
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("6").performClick()
        composeTestRule.onNodeWithText("( )").performClick()
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("( )").performClick()
        composeTestRule.onNodeWithText("4").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("-").performClick()
        composeTestRule.onNodeWithText("5").performClick()

        composeTestRule.onNodeWithText("=").performClick()

       composeTestRule.onNodeWithText("240.00").assertExists("No node with this result")

    }


    @Test
    fun calculatorUi_decimalNumbersOperation_operationWithDecimalsResult() {
        //operation: "((2.5+0.8)x(1.2-0.3))"
        composeTestRule.setContent {
            SimpleCalculatorTheme {
                CalculatorScreen()
            }
        }

        composeTestRule.onNodeWithText("( )").performClick()
        composeTestRule.onNodeWithText("( )").performClick()
        composeTestRule.onNodeWithText("2").performClick()
        composeTestRule.onNodeWithText(".").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText(".").performClick()
        composeTestRule.onNodeWithText("8").performClick()
        composeTestRule.onNodeWithText("( )").performClick()
        composeTestRule.onNodeWithText("x").performClick()
        composeTestRule.onNodeWithText("( )").performClick()
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText(".").performClick()
        composeTestRule.onNodeWithText("2").performClick()
        composeTestRule.onNodeWithText("-").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText(".").performClick()
        composeTestRule.onNodeWithText("3").performClick()
        composeTestRule.onNodeWithText("( )").performClick()
        composeTestRule.onNodeWithText("( )").performClick()

        composeTestRule.onNodeWithText("=").performClick()

        composeTestRule.onNodeWithText("2.97").assertExists("No node with this result")
    }


    @Test
    fun calculatorUi_combinedComplexOperation_operationResult() {
        //operation: "((((12.5+8.3)*(4+6)+(45-5)%)x2.35" = 208.94

        composeTestRule.setContent {
            SimpleCalculatorTheme {
                CalculatorScreen()
            }
        }

        repeat(4) { composeTestRule.onNodeWithText("( )").performClick() }
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("2").performClick()
        composeTestRule.onNodeWithText(".").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("8").performClick()
        composeTestRule.onNodeWithText(".").performClick()
        composeTestRule.onNodeWithText("3").performClick()
        composeTestRule.onNodeWithText("( )").performClick()
        composeTestRule.onNodeWithText("x").performClick()
        composeTestRule.onNodeWithText("( )").performClick()
        composeTestRule.onNodeWithText("4").performClick()
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("6").performClick()
        composeTestRule.onNodeWithText("( )").performClick()
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("( )").performClick()
        composeTestRule.onNodeWithText("4").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("-").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("( )").performClick()
        composeTestRule.onNodeWithText("%").performClick()
        composeTestRule.onNodeWithText("x").performClick()
        composeTestRule.onNodeWithText("2").performClick()
        composeTestRule.onNodeWithText(".").performClick()
        composeTestRule.onNodeWithText("3").performClick()
        composeTestRule.onNodeWithText("5").performClick()

        composeTestRule.onNodeWithText("=").performClick()

        composeTestRule.onNodeWithText("208.94").assertExists("No node with this result")

    }

    @Test
    fun calculatorUi_signToggle_negatesEnteredNumber() {
        //operation: "5" -> "+/-" -> "-5" -> "=" -> "-5.00"
        composeTestRule.setContent {
            SimpleCalculatorTheme {
                CalculatorScreen()
            }
        }

        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("+/-").performClick()
        composeTestRule.onNodeWithText("=").performClick()

        composeTestRule.onNodeWithText("-5.00").assertExists("No node with this result")
    }

    //--- Calculation history log tests ---

    @Test
    fun calculatorUi_tapHistoryButton_opensHistoryDialog() {
        composeTestRule.setContent {
            SimpleCalculatorTheme {
                CalculatorScreen()
            }
        }

        composeTestRule.onNodeWithContentDescription("view calculation history").performClick()

        composeTestRule.onNodeWithText("HISTORY").assertExists("History dialog did not open")
        composeTestRule.onNodeWithText("No calculations yet").assertExists("Empty state not shown")
    }

    @Test
    fun calculatorUi_openHistoryAfterCalculation_showsExpressionAndResult() {
        composeTestRule.setContent {
            SimpleCalculatorTheme {
                CalculatorScreen()
            }
        }

        //operation: "7+2" = "9.00"
        composeTestRule.onNodeWithText("7").performClick()
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("2").performClick()
        composeTestRule.onNodeWithText("=").performClick()

        //clear the display first (history is preserved across AC) so the expression/
        //result assertions below match exactly one node (the history row), not two
        composeTestRule.onNodeWithText("AC").performClick()

        composeTestRule.onNodeWithContentDescription("view calculation history").performClick()

        composeTestRule.onNodeWithText("7+2").assertExists("Expected expression missing from history")
        composeTestRule.onNodeWithText("9.00").assertExists("Expected result missing from history")
    }

    @Test
    fun calculatorUi_tapHistoryEntry_loadsItBackAndDismissesDialog() {
        composeTestRule.setContent {
            SimpleCalculatorTheme {
                CalculatorScreen()
            }
        }

        //operation: "7+2" = "9.00"
        composeTestRule.onNodeWithText("7").performClick()
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("2").performClick()
        composeTestRule.onNodeWithText("=").performClick()

        composeTestRule.onNodeWithText("AC").performClick()

        composeTestRule.onNodeWithContentDescription("view calculation history").performClick()
        composeTestRule.onNodeWithText("7+2").performClick()

        composeTestRule.onNodeWithText("HISTORY").assertDoesNotExist()
        composeTestRule.onNodeWithText("7+2").assertExists("Expression was not recalled into the display")
        composeTestRule.onNodeWithText("9.00").assertExists("Result was not recalled into the display")
    }

    @Test
    fun calculatorUi_clearHistory_requiresConfirmation() {
        composeTestRule.setContent {
            SimpleCalculatorTheme {
                CalculatorScreen()
            }
        }

        //operation: "3+3" = "6.00"
        composeTestRule.onNodeWithText("3").performClick()
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("3").performClick()
        composeTestRule.onNodeWithText("=").performClick()

        composeTestRule.onNodeWithText("AC").performClick()

        composeTestRule.onNodeWithContentDescription("view calculation history").performClick()
        composeTestRule.onNodeWithText("CLEAR").performClick()

        composeTestRule.onNodeWithText("Clear history?").assertExists("Confirmation dialog did not appear")

        //cancel: entry must remain
        composeTestRule.onNodeWithText("CANCEL").performClick()
        composeTestRule.onNodeWithText("Clear history?").assertDoesNotExist()
        composeTestRule.onNodeWithText("3+3").assertExists("Cancel must not clear history")

        //confirm: entry must be removed, history view stays open showing empty state
        composeTestRule.onNodeWithText("CLEAR").performClick()
        composeTestRule.onNodeWithText("CONFIRM").performClick()

        composeTestRule.onNodeWithText("HISTORY").assertExists("History dialog should remain open after clearing")
        composeTestRule.onNodeWithText("No calculations yet").assertExists("History was not cleared")
        composeTestRule.onNodeWithText("3+3").assertDoesNotExist()
    }
}