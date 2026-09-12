package com.example.simplecalculator.ui

import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CalculatorViewModel: ViewModel() {
    companion object {
        private const val MAX_HISTORY_SIZE = 20
    }

    //Calculator UI state
    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow(CalculatorUiState())
    val uiState: StateFlow<CalculatorUiState> = _uiState.asStateFlow()

    private var parenthesisCount = 0
    private var mapParenthesis = mutableMapOf<Array<Int>, Int>()

    private var auxOperation = ""

    fun clearDisplay() {
        // Session-only history must survive clearing the current display/expression
        // (AC), so it is explicitly carried over instead of being reset along with
        // every other field.
        val history = _uiState.value.history
        _uiState.value = CalculatorUiState(history = history)
        parenthesisCount = 0
        mapParenthesis.clear()
        auxOperation = ""
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
        var auxAppend = ""

        //check operation append
        if(lastChar == null) {
            auxAppend = "0$appendOperation"
        }
        else if(appendOperation == '%') {
            if(isNumber(lastChar) && lastChar != '.' || lastChar == ')') {
                auxAppend = "%"
            }
        }
        else if(appendOperation == '.') {
            val flag = _uiState.value.currentOperation.contains(Regex("[0-9]+\\.[0-9]+\\Z"))
            if (isNumber(lastChar) && lastChar != '.' && !flag) {
                auxAppend = "."
            }
        }
        else {
            if(lastChar !in ")%" && !isNumber(lastChar) && !isNumber(appendOperation)) {
                backspace()
            }
            auxAppend = appendOperation.toString()
        }

        _uiState.update { currentState ->
            currentState.copy(
                currentOperation = _uiState.value.currentOperation + auxAppend
            )
        }
    }

    private fun isNumber(char: Char): Boolean {
        return char in ".0123456789"
    }

    fun toggleSign() {
        val op = _uiState.value.currentOperation
        var numStart = op.length
        while (numStart > 0 && isNumber(op[numStart - 1])) numStart--

        val c1 = op.getOrNull(numStart - 1)

        val newOp = when (c1) {
            ')', '%' -> return
            '+' -> op.replaceRange(numStart - 1, numStart, "-")
            '-' -> {
                val c0 = if (numStart >= 2) op[numStart - 2] else null
                val isBinaryOperator = c0 != null && (isNumber(c0) || c0 == ')' || c0 == '%')
                if (isBinaryOperator) op.replaceRange(numStart - 1, numStart, "+")
                else op.removeRange(numStart - 1, numStart)
            }
            else -> op.substring(0, numStart) + "-" + op.substring(numStart)
        }

        _uiState.update { currentState ->
            currentState.copy(
                currentOperation = newOp
            )
        }
    }

    fun calculateResult() {
        val result = resolveCalculation()
        // Read after resolveCalculation() so any parenthesis auto-closed by
        // solveParenthesis() is reflected in the captured history expression.
        val expression = _uiState.value.currentOperation

        _uiState.update { currentState ->
            val updatedHistory = if (result.toDoubleOrNull() != null) {
                (listOf(HistoryEntry(expression, result)) + currentState.history)
                    .take(MAX_HISTORY_SIZE)
            } else {
                currentState.history
            }

            currentState.copy(
                result = result,
                history = updatedHistory
            )
        }
    }

    fun openHistory() {
        _uiState.update { currentState ->
            currentState.copy(isHistoryVisible = true)
        }
    }

    fun closeHistory() {
        _uiState.update { currentState ->
            currentState.copy(isHistoryVisible = false)
        }
    }

    fun clearHistory() {
        _uiState.update { currentState ->
            currentState.copy(history = emptyList())
        }
    }

    /**
     * Re-parses [expression] to rebuild [mapParenthesis]/[parenthesisCount] from
     * scratch. Required before loading a recalled history entry back into the
     * display: backspace()/parenthesis() both do exact-match lookups against
     * mapParenthesis, and those structures are otherwise only ever built
     * incrementally as the user types, not from an arbitrary pre-existing string.
     */
    private fun rebuildParenthesisState(expression: String) {
        mapParenthesis.clear()
        val stack = mutableListOf<Pair<Int, Int>>()

        expression.forEachIndexed { index, char ->
            when (char) {
                '(' -> stack.add(stack.size to index)
                ')' -> {
                    if (stack.isNotEmpty()) {
                        val (depthAtOpenTime, openIndex) = stack.removeAt(stack.size - 1)
                        mapParenthesis[arrayOf(depthAtOpenTime, openIndex)] = index
                    }
                }
            }
        }

        parenthesisCount = stack.size
        auxOperation = ""
    }

    fun selectHistoryEntry(entry: HistoryEntry) {
        rebuildParenthesisState(entry.expression)

        _uiState.update { currentState ->
            currentState.copy(
                currentOperation = entry.expression,
                result = entry.result,
                currentOperationFontSize = 48.sp,
                isHistoryVisible = false
            )
        }
    }

    fun backspace() {
        val lastChar = _uiState.value.currentOperation.lastOrNull()

        if(lastChar == '(') {
            mapParenthesis.remove(
                mapParenthesis.filter { element ->
                    element.key[0] == parenthesisCount - 1
                            && element.key[1] < _uiState.value.currentOperation.length
                            && element.value == -1
                }.keys.first()
            )
            parenthesisCount--
        } else if(lastChar == ')') {
            val aux = mapParenthesis.filter {
                    element -> element.value == _uiState.value.currentOperation.length - 1
            }
            parenthesisCount++
            mapParenthesis[aux.keys.first()] = -1
        }

        if (lastChar != null) {
            _uiState.update { currentState ->
                val auxFontSize = when(_uiState.value.currentOperationFontSize * 1.05 > 48.sp) {
                    true -> 48.sp
                    false -> _uiState.value.currentOperationFontSize * 1.05
                }
                currentState.copy(
                    currentOperation = _uiState.value.currentOperation.dropLast(1),
                    currentOperationFontSize = auxFontSize
                )
            }
        }
    }

    fun parenthesis() {
        var append = ""

        val last = _uiState.value.currentOperation.lastOrNull()

        //Case 1: first input in operation -> '('
        //Case 2: input after operation symbol or open parenthesis -> '('
        //Case 3: input after number, no open parenthesis before -> '('
        if(last == null || parenthesisCount == 0 || (!isNumber(last) && last !in ")%" )
            || last == '(' ) {
            append = "("
            mapParenthesis[arrayOf(parenthesisCount, _uiState.value.currentOperation.length)] = -1
            parenthesisCount++
        } else {
            //Case 4: input after number, opened parenthesis before -> ')'
            //Case 5: input after close parenthesis, opened parenthesis before -> ')'
            if ((isNumber(last) || last in ")%") && parenthesisCount > 0) {
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

        try {
            solveParenthesis()
            val subResult =  calculateSubResult(auxOperation)

            if (auxOperation.contains("Cannot be divided by 0")) {
                return auxOperation
            }
            if (subResult.contains('E')) return subResult

            return "%.2f".format(subResult.replace(',', '.').toDouble())
                .replace(',', '.')
        } catch (e: Exception) {
            return "Syntax error"
        }
    }

    private fun calculateSubResult(operation: String): String {

        if(operation.contains("Cannot be divided by 0")) {
            return "Cannot be divided by 0"
        }
        var temp = operation
        while (temp.contains('%')) {
            temp = solvePercentages(temp)
        }

        val result = when(temp.firstOrNull { !isNumber(it) }) {
            'x' -> { multiply(temp) }
            '/' -> { divide(temp) }
            '+' -> { add(temp) }
            '-' -> { subtract(temp) }
            else -> { temp }
        }

        return result
    }

    private fun subtract(operation: String): String {
        val result = simplify(operation, '-')

        return formatResult(result[0] - result[1])
    }

    private fun add(operation: String): String {
        val result = simplify(operation, '+')

        return formatResult(result[0] + result[1])
    }

    private fun divide(operation: String): String {
        val result = simplify(operation, '/')

        return if( result[1] != 0.0) {
            formatResult(result[0] / result[1])
        } else {
            auxOperation = "Cannot be divided by 0"
            "0.0"
        }
    }


    private fun multiply(operation: String): String {
        val result = simplify(operation, 'x')

        return formatResult(result[0] * result[1])
    }

    private fun formatResult(number: Double): String {
        if (number.toString().contains('E')) {
            return number.toString()
        }

        return "%.4f".format(number)
    }
    private fun solvePercentages(operation: String): String {
        //case 1: percentage after sum or sub: 15+30%
        //case 2: percentage after multiply/divide: 10x50%
        //case 3: percentage after parenthesis: (4+6)50%
        //case 4: multiple % operations: 50%%%

        auxOperation = operation
        while(auxOperation.contains('%')) {
            when(true) {
                auxOperation.contains(Regex("[0.0-9]+[x/][0.0-9]+%"))  -> {
                    val found = Regex("[0.0-9]+[x/][0.0-9]+%").find(auxOperation)//check
                    auxOperation = auxOperation.replaceRange(
                        found!!.range, found.value.replace("%", "/100")
                    )
                    //offset mapParenthesis
                    val indexOfPercentage = found.value.indexOf('%') + found.range.first
                    val offset = 3

                    offsetParenthesis(offset, indexOfPercentage)
                }

                auxOperation.contains(Regex("[0.0-9]+%[x/][0.0-9]+")) -> {
                    val found = Regex("[0.0-9]+%[x/][0.0-9]+").find(auxOperation)

                    val index = found!!.value.indexOf('%')
                    val sub1 = found.value.substring(0, index )
                    val sub2 = found.value.substring(index+1)

                    val newValue = calculateSubResult("${sub1}/100")
                    auxOperation = auxOperation.replaceRange(
                        found.range, "${newValue}${sub2}"
                    )
                    //offset mapParenthesis
                    val indexOfPercentage = found.value.indexOf('%') + found.range.first
                    val offset = (newValue.length + sub2.length) - found.value.length

                    offsetParenthesis(offset, indexOfPercentage)
                }

                auxOperation.contains(Regex("[+-]?[0.0-9]+[+-][0.0-9]+%[+-]?")) -> {
                    val found = Regex("[+-]?[0.0-9]+[+-][0.0-9]+%[+-]?").find(auxOperation)

                    var foundValue = found?.value

                    if(foundValue!!.first() in "+-") foundValue = foundValue.drop(1)
                    if(foundValue.last() in "+-") foundValue = foundValue.dropLast(1)

                    val operatorIndex = foundValue.indexOfFirst { it in "+-" }

                    val sub1 = foundValue.substring(0, operatorIndex)

                    auxOperation = auxOperation.replaceRange(
                        found!!.range, found.value.replace("%", "x${sub1}/100")
                    )
                    //offset mapParenthesis
                    val indexOfPercentage = found.value.indexOf('%') + found.range.first
                    val offset = sub1.length + 4

                    offsetParenthesis(offset, indexOfPercentage)
                }

                else -> {
                    //replace % by /100
                    val indexOfPercentage = auxOperation.indexOfFirst { it == '%' }
                    auxOperation = auxOperation.replaceRange(
                        indexOfPercentage, indexOfPercentage + 1, "/100")
                    val offset = 3
                    offsetParenthesis(offset, indexOfPercentage)
                }
            }
        }
        return auxOperation
    }

    private fun offsetParenthesis(offset: Int, index: Int) {
        //offset mapParenthesis

        val auxMap = mapParenthesis.toMutableMap()

        mapParenthesis.forEach { element ->
            if (element.key[1] > index) {
                auxMap.remove(element.key)
                auxMap[arrayOf(element.key[0], element.key[1]  + offset)] =
                    element.value + offset
            }
            else if (element.value > index) {
                auxMap[element.key] = element.value + offset
            }
        }
        mapParenthesis = auxMap.toMutableMap()
    }

    private fun operandBoundaryIndex(sub2: String): Int {
        //skip a leading sign character that belongs to sub2's own operand
        //(e.g. sub2 == "-3" in "5x-3") before scanning for the next +/-/% boundary
        val scanStart = if (sub2.isNotEmpty() && sub2[0] in "+-") 1 else 0
        val relativeIndex = sub2.substring(scanStart).indexOfFirst { it in "+-%" }
        return if (relativeIndex == -1) -1 else relativeIndex + scanStart
    }

    private fun simplify(operation: String, operator: Char): Array<Double> {

        val operatorIndex = operation.indexOf(operator)

        var sub1 = operation.substring(0, operatorIndex)
        var sub2 = operation.substring(operatorIndex + 1)

        //when the operation itself starts with `operator` (e.g. calculateSubResult("-3")),
        //sub1 is empty; default to the leading-zero convention used elsewhere in the file
        if (sub1.isEmpty()) sub1 = "0"

        if(
        operator in "x/"
        && !sub2.all { isNumber(it) }
        && operandBoundaryIndex(sub2) != -1 ) {

            val i = operandBoundaryIndex(sub2)
            if(sub2[i] == '%') {
                sub1 = multiply("${sub1}x${sub2.substring(0, i)}/100")
                val j = sub2.indexOfFirst { it in "+-" }
                sub2 = if (j != -1) sub2.substring(j) else sub2

                sub1 = calculateSubResult("${sub1}${sub2}")

                return arrayOf(sub1.replace(',', '.').toDouble(), 1.0)
            }
            when(operator) {
                'x' -> { sub1 = multiply("${sub1}x${sub2.substring(0, i)}") }
                '/' -> { sub1 = divide("${sub1}/${sub2.substring(0, i)}") }
            }
            sub1 = calculateSubResult("${sub1}${sub2.substring(i)}")
            return arrayOf(sub1.replace(',', '.').toDouble(), 1.0)
        }

        if ( !sub2.all { isNumber(it) }) {
            if (operator == '-') {
                sub2 = sub2.replace('-', '#')
                sub2 = sub2.replace('+', '-')
                sub2 = sub2.replace('#', '+')
            }
            sub2 = calculateSubResult(sub2)
        }

        val num1 = sub1.replace(',', '.').toDouble()
        val num2 = sub2.replace(',', '.').toDouble()

        return arrayOf(num1, num2)
    }
 

    private fun solveParenthesis() {
        //check if any parenthesis was left open
        auxOperation = _uiState.value.currentOperation

        //add close parenthesis when user left them open
        if (parenthesisCount != 0) {
            auxOperation = auxOperation.plus(")".repeat(parenthesisCount))

            repeat(parenthesisCount) { parenthesis() }
            parenthesisCount = 0
        }

        var auxMap = mapParenthesis.toMutableMap()

        while (auxMap.isNotEmpty()) {
            val offset = auxOperation.length
            val aux = auxMap.maxBy { it.key[0] }

            val startIndex = aux.key[1] + 1
            val endIndex = aux.value

            auxOperation = auxOperation.replaceRange(
                startIndex = startIndex - 1,
                endIndex = endIndex + 1,
                replacement = calculateSubResult(auxOperation.substring(startIndex, endIndex))
            )

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

    fun resizeCurrentResultFontSize() {
        _uiState.update { currentOperation ->
            currentOperation.copy(
                currentOperationFontSize = _uiState.value.currentOperationFontSize * 0.95
            )
        }
    }
}