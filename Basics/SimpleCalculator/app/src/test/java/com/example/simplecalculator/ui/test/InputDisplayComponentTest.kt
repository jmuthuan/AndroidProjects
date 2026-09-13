package com.example.simplecalculator.ui.test

import com.example.simplecalculator.ui.isResultCopyable
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class InputDisplayComponentTest {

    //fun thingUnderTest_TriggerOfTest_ResultOfTest()
    @Test
    fun isResultCopyable_positiveNumericResult_returnsTrue() {
        assertTrue(isResultCopyable("150.00"))
    }

    @Test
    fun isResultCopyable_negativeNumericResult_returnsTrue() {
        assertTrue(isResultCopyable("-42.5"))
    }

    @Test
    fun isResultCopyable_blankResult_returnsFalse() {
        assertFalse(isResultCopyable(""))
    }

    @Test
    fun isResultCopyable_syntaxErrorResult_returnsFalse() {
        assertFalse(isResultCopyable("Syntax error"))
    }

    @Test
    fun isResultCopyable_divisionByZeroResult_returnsFalse() {
        assertFalse(isResultCopyable("Cannot be divided by 0"))
    }
}
