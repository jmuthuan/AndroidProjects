package com.example.simplecalculator

import androidx.compose.ui.test.junit4.createComposeRule
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
}