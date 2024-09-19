package com.example.simplecalculator.ui

import android.content.Context
import android.os.Vibrator
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simplecalculator.R
import com.example.simplecalculator.ui.theme.EqualButtonColor
import com.example.simplecalculator.ui.theme.NumberButtonsColor
import com.example.simplecalculator.ui.theme.OperationButtonsColor
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme
import com.example.simplecalculator.utils.vibrationClick

@Composable
fun InputButtonsComponent(calculatorViewModel: CalculatorViewModel, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxHeight()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ButtonComponent(
                color = OperationButtonsColor,
                symbol = stringResource(id = R.string.clear),
                modifier = Modifier.weight(1f),
                onClick = {
                    vibrationClick(vibrator)
                    calculatorViewModel.clearDisplay()
                }
            )

            ButtonComponent(
                color = OperationButtonsColor,
                modifier = Modifier.weight(1f),
                image = Pair(
                    first = painterResource(id = R.drawable.baseline_backspace_24),
                    second = "erase last operation"
                    ),
                onClick = {
                    vibrationClick(vibrator)
                    calculatorViewModel.backspace()
                }
            )

            ButtonComponent(
                color = OperationButtonsColor,
                symbol = stringResource(id = R.string.parenthesis),
                modifier = Modifier.weight(1f),
                onClick = {
                    vibrationClick(vibrator)
                    calculatorViewModel.parenthesis()
                }
            )

            ButtonComponent(
                color = OperationButtonsColor,
                symbol = stringResource(id = R.string.percentage),
                modifier = Modifier.weight(1f),
                onClick = {
                    vibrationClick(vibrator)
                    calculatorViewModel.updateOperation('%')
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ButtonComponent(
                color = NumberButtonsColor,
                symbol = stringResource(id = R.string.seven),
                modifier = Modifier.weight(1f),
                onClick = {
                    vibrationClick(vibrator)
                    calculatorViewModel.enterNumber('7')
                }
            )

            ButtonComponent(
                color = NumberButtonsColor,
                symbol = stringResource(id = R.string.eight),
                modifier = Modifier.weight(1f),
                onClick = {
                    vibrationClick(vibrator)
                    calculatorViewModel.enterNumber('8')
                }
            )

            ButtonComponent(
                color = NumberButtonsColor,
                symbol = stringResource(id = R.string.nine),
                modifier = Modifier.weight(1f),
                onClick = {
                    vibrationClick(vibrator)
                    calculatorViewModel.enterNumber('9')
                }
            )

            ButtonComponent(
                color = OperationButtonsColor,
                symbol = stringResource(id = R.string.divide),
                modifier = Modifier.weight(1f),
                onClick = {
                    vibrationClick(vibrator)
                    calculatorViewModel.updateOperation('/')
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ButtonComponent(
                color = NumberButtonsColor,
                symbol = stringResource(id = R.string.four),
                modifier = Modifier.weight(1f),
                onClick = {
                    vibrationClick(vibrator)
                    calculatorViewModel.enterNumber('4')
                }
            )

            ButtonComponent(
                color = NumberButtonsColor,
                symbol = stringResource(id = R.string.five),
                modifier = Modifier.weight(1f),
                onClick = {
                    vibrationClick(vibrator)
                    calculatorViewModel.enterNumber('5')
                }
            )

            ButtonComponent(
                color = NumberButtonsColor,
                symbol = stringResource(id = R.string.six),
                modifier = Modifier.weight(1f),
                onClick = {
                    vibrationClick(vibrator)
                    calculatorViewModel.enterNumber('6')
                }
            )

            ButtonComponent(
                color = OperationButtonsColor,
                symbol = stringResource(id = R.string.multiply),
                modifier = Modifier.weight(1f),
                onClick = {
                    vibrationClick(vibrator)
                    calculatorViewModel.updateOperation('x')
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ButtonComponent(
                color = NumberButtonsColor,
                symbol = stringResource(id = R.string.one),
                modifier = Modifier.weight(1f),
                onClick = {
                    vibrationClick(vibrator)
                    calculatorViewModel.enterNumber('1')
                }
            )

            ButtonComponent(
                color = NumberButtonsColor,
                symbol = stringResource(id = R.string.two),
                modifier = Modifier.weight(1f),
                onClick = {
                    vibrationClick(vibrator)
                    calculatorViewModel.enterNumber('2')
                }
            )

            ButtonComponent(
                color = NumberButtonsColor,
                symbol = stringResource(id = R.string.three),
                modifier = Modifier.weight(1f),
                onClick = {
                    vibrationClick(vibrator)
                    calculatorViewModel.enterNumber('3')
                }
            )

            ButtonComponent(
                color = OperationButtonsColor,
                symbol = stringResource(id = R.string.minus),
                modifier = Modifier.weight(1f),
                onClick = {
                    vibrationClick(vibrator)
                    calculatorViewModel.updateOperation('-')
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ButtonComponent(
                color = NumberButtonsColor,
                symbol = stringResource(id = R.string.zero),
                modifier = Modifier.weight(1f),
                onClick = {
                    vibrationClick(vibrator)
                    calculatorViewModel.enterNumber('0')
                }
            )

            ButtonComponent(
                color = NumberButtonsColor,
                symbol = stringResource(id = R.string.dot),
                modifier = Modifier.weight(1f),
                onClick = {
                    vibrationClick(vibrator)
                    calculatorViewModel.updateOperation('.')
                }
            )

            ButtonComponent(
                color = EqualButtonColor,
                symbol = stringResource(id = R.string.equal),
                modifier = Modifier.weight(1f),
                onClick = {
                    vibrationClick(vibrator)
                    calculatorViewModel.calculateResult()
                }
            )

            ButtonComponent(
                color = OperationButtonsColor,
                symbol = stringResource(id = R.string.plus),
                modifier = Modifier.weight(1f),
                onClick = {
                    vibrationClick(vibrator)
                    calculatorViewModel.updateOperation('+')
                }
            )
        }
    }
}


@Preview
@Composable
fun InputButtonsComponentPreview() {
    SimpleCalculatorTheme {
        Box(modifier =
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
        ) {
            InputButtonsComponent(calculatorViewModel = CalculatorViewModel())
        }
    }
}