package com.example.simplecalculator.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CalculatorViewModel: ViewModel() {
    //Calculator UI state
    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow(CalculatorUiState())
    val uiState: StateFlow<CalculatorUiState> = _uiState.asStateFlow()

    private var openParenthesis = true
    private var parenthesisCount = 0
    private var mapParenthesis = mutableMapOf<Array<Int>, Int>()

    private var auxOperation = ""

    fun clearDisplay() {
        _uiState.value = CalculatorUiState()
        openParenthesis = true
        parenthesisCount = 0
    }

    init {
        clearDisplay()
    }

    fun enterNumber(number: Char) {
        val append = if(_uiState.value.currentOperation.lastOrNull() == ')') "x$number" else number
        _uiState.update { currentState ->
            currentState.copy(
                currentOperation = _uiState.value.currentOperation + append
            )
        }
    }
    //Operations: . , +, -, /, *, %
    fun updateOperation(appendOperation: Char) {
        checkOperation(appendOperation)
    }

    private fun checkOperation(appendOperation: Char) {
        //get last operation character
        val lastChar = _uiState.value.currentOperation.lastOrNull()

        //check operation append
        if(lastChar == null) {
            _uiState.update { currentState ->
                currentState.copy(
                    currentOperation = "0$appendOperation"
                )
            }
        } else {
            if(lastChar != ')' && !isNumber(lastChar) && !isNumber(appendOperation)) {
                backspace()
            }
            _uiState.update { currentState ->
                currentState.copy(
                    currentOperation = _uiState.value.currentOperation + appendOperation
                )
            }
        }
    }

    private fun isNumber(char: Char): Boolean {
        return char in ".0123456789"
    }

    fun calculateResult() {
        _uiState.update { currentState ->
            currentState.copy(
                result =  resolveCalculation()
            )
        }
    }

    fun backspace() {
        if(_uiState.value.currentOperation.last() == '(') {
//            val aux = mapParenthesis.keys
            mapParenthesis.remove(
                mapParenthesis.filter { element ->
                    element.key[0] == parenthesisCount - 1
                            && element.key[1] < _uiState.value.currentOperation.length
                            && element.value == -1
                }.keys.first()
            )
            parenthesisCount--
        } else if(_uiState.value.currentOperation.last() == ')') {
            val aux = mapParenthesis.filter {
                    element -> element.value == _uiState.value.currentOperation.length - 1
            }
            parenthesisCount++
            mapParenthesis[aux.keys.first()] = -1
        }

        _uiState.update { currentState ->
            currentState.copy(
                currentOperation = _uiState.value.currentOperation.dropLast(1)
            )
        }
    }

    fun parenthesis() {
        var append = ""

        val last = _uiState.value.currentOperation.lastOrNull()

        //Case 1: first input in operation -> '('
        //Case 2: input after operation symbol or open parenthesis -> '('
        //Case 3: input after number, no open parenthesis before -> '('
        if(last == null || parenthesisCount == 0 || (!isNumber(last) && last != ')')
            || last == '(' ) {
            append = "("
            mapParenthesis[arrayOf(parenthesisCount, _uiState.value.currentOperation.length)] = -1
            parenthesisCount++
        } else {
            //Case 4: input after number, opened parenthesis before -> ')'
            //Case 5: input after close parenthesis, opened parenthesis before -> ')'
            if ((isNumber(last) || last == ')') && parenthesisCount > 0) {
                append = ")"

                val aux = mapParenthesis.filter { element ->
                    element.key[0] == parenthesisCount - 1
                            && element.key[1] < _uiState.value.currentOperation.length
                            && element.value == -1
                }
                mapParenthesis[aux.keys.first()] = _uiState.value.currentOperation.length
                parenthesisCount--
            }
        }

        _uiState.update { currentState ->
            currentState.copy(
                currentOperation = _uiState.value.currentOperation + append
            )
        }
    }

    private fun resolveCalculation(): String {
        //step 1: solve parenthesis
        //step 2: solve multiplication & division
        //step 3: solve addition and subtraction

        solveParenthesis()
        return calculateSubResult(auxOperation).toString()

    }

    private fun calculateSubResult(operation: String): Double {

        val result = when(operation.firstOrNull { !isNumber(it) }) {
            'x' -> { multiply(operation) }
            '/' -> { divide(operation)}
            '+' -> { add(operation) }
            '-' -> { subtract(operation)}
            else -> { operation.toDouble()}
        }

        return result
    }

    private fun subtract(operation: String): Double {
        val result = simplify(operation, '-')

        return result[0] - result[1]
    }

    private fun add(operation: String): Double {
        val result = simplify(operation, '+')

        return result[0] + result[1]
    }

    private fun divide(operation: String): Double {
        val result = simplify(operation, '/')

        return result[0] / result[1]
    }


    private fun multiply(operation: String): Double {
        val result = simplify(operation, 'x')

        return result[0] * result[1]
    }

    private fun simplify(operation: String, operator: Char): Array<Double> {

        val operatorIndex = operation.indexOf(operator)

        var sub1 = operation.substring(0, operatorIndex)
        var sub2 = operation.substring(operatorIndex + 1)

        if(
            operator in "x/"
            && !sub2.all { isNumber(it) }
            && sub2.indexOfFirst { it in "+-" } != -1
            ) {

            val i = sub2.indexOfFirst { it in "+-" }
//            val auxOperator = sub2[i]
            when(operator) {
                'x' -> {
                    sub1 =
                        multiply("${sub1}x${sub2.substring(0, i)}")
                            .toString()
                }
                '/' -> {
                    sub1 =
                        divide("${sub1}/${sub2.substring(0, i)}")
                        .toString()
                }
            }
            sub1 =
                calculateSubResult("${sub1}${sub2.substring(i)}")
                    .toString()
            return arrayOf(sub1.toDouble(), 1.0)
        }

        if ( !sub2.all { isNumber(it) }) {
            if (operator == '-') {
                sub2 = sub2.replace('-', '#')
                sub2 = sub2.replace('+', '-')
                sub2 = sub2.replace('#', '+')
            }
            sub2 = calculateSubResult(sub2).toString()
        }

        val num1 = sub1.toDouble() //operation.substring(startIndex, operatorIndex).toInt()
        val num2 = sub2.toDouble() //operation.substring(operatorIndex + 1, endIndex).toInt()

        return arrayOf(num1, num2)
    }
 

    private fun solveParenthesis() {
        //check if any parenthesis was left open
        auxOperation = _uiState.value.currentOperation

        if (parenthesisCount != 0) {
            auxOperation = auxOperation.plus(")".repeat(parenthesisCount))

            repeat(parenthesisCount) { parenthesis() }
            parenthesisCount = 0
        }

        var auxMap = mapParenthesis

        while (auxMap.isNotEmpty()) {
            val offset = auxOperation.length
            val aux = auxMap.maxBy { it.key[0] }
            var startIndex = 0
            var endIndex = auxOperation.length

            startIndex = aux.key[1] + 1
            endIndex = aux.value

            auxOperation = auxOperation.replaceRange(
                startIndex = startIndex - 1,
                endIndex = endIndex + 1,
                replacement = calculateSubResult(
                    auxOperation.substring(startIndex, endIndex)).toString())

            auxMap.remove(aux.key)

            val offsetMap = auxMap.toMutableMap()

            auxMap.forEach {

                if(it.key[1] > aux.value) {
                    offsetMap[arrayOf(it.key[0], it.key[1] - offset + auxOperation.length)] =
                        it.value - offset + auxOperation.length
                    offsetMap.remove(it.key)
                }else if(it.value > aux.value) {
                    offsetMap[it.key] = it.value - offset + auxOperation.length
                }

            }
            auxMap = offsetMap.toMutableMap()
        }
    }
}