package com.example.simplecalculator

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.simplecalculator.ui.CalculatorScreen
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class SimpleCalculatorUiTest {
    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun calculatorViewModel_parenthesisSolving_operationWithParenthesisResult() {
        composeTestRule.setContent {
            SimpleCalculatorTheme {
                CalculatorScreen()
            }
        }

        //operation: "((12+8)*(4+6)+(45-5))"
        repeat(2){ composeTestRule.onNodeWithText("()").performClick() }
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("2").performClick()
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("8").performClick()
        composeTestRule.onNodeWithText("()").performClick()
        composeTestRule.onNodeWithText("x").performClick()
        composeTestRule.onNodeWithText("()").performClick()
        composeTestRule.onNodeWithText("4").performClick()
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("6").performClick()
        composeTestRule.onNodeWithText("()").performClick()
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("()").performClick()
        composeTestRule.onNodeWithText("4").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("-").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        repeat(2){ composeTestRule.onNodeWithText("()").performClick() }

        composeTestRule.onNodeWithText("=").performClick()


        composeTestRule.onNodeWithText("240.0").assertExists("No node with this result")
    }


    @Test
    fun calculatorViewModel_parenthesisLeftOpenSolving_operationWithOpenParenthesisResult() {
        composeTestRule.setContent {
            SimpleCalculatorTheme {
                CalculatorScreen()
            }
        }

        //operation: "((12+8)*(4+6)+(45-5"
        repeat(2){ composeTestRule.onNodeWithText("()").performClick() }
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("2").performClick()
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("8").performClick()
        composeTestRule.onNodeWithText("()").performClick()
        composeTestRule.onNodeWithText("x").performClick()
        composeTestRule.onNodeWithText("()").performClick()
        composeTestRule.onNodeWithText("4").performClick()
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("6").performClick()
        composeTestRule.onNodeWithText("()").performClick()
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("()").performClick()
        composeTestRule.onNodeWithText("4").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("-").performClick()
        composeTestRule.onNodeWithText("5").performClick()

        composeTestRule.onNodeWithText("=").performClick()

       composeTestRule.onNodeWithText("240.0").assertExists("No node with this result")

    }
}