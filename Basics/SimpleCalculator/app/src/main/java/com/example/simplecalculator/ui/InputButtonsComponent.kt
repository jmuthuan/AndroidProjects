package com.example.simplecalculator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simplecalculator.R
import com.example.simplecalculator.ui.theme.EqualButtonColor
import com.example.simplecalculator.ui.theme.NumberButtonsColor
import com.example.simplecalculator.ui.theme.OperationButtonsColor
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme

@Composable
fun InputButtonsComponent(calculatorViewModel: CalculatorViewModel, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
//        modifier = modifier.padding(top = 64.dp)

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ButtonComponent(
                color = OperationButtonsColor,
                symbol = stringResource(id = R.string.clear),
                modifier = Modifier.weight(1f),
                onClick = { calculatorViewModel.clearDisplay() }
            )

            ButtonComponent(
                color = OperationButtonsColor,
                modifier = Modifier.weight(1f),
                //            image = Image(
                //                painter = painterResource(id = R.drawable.baseline_backspace_24),
                //                contentDescription = "erase last operation"
                //            ),
                onClick = { calculatorViewModel.backspace() }
            )

            ButtonComponent(
                color = OperationButtonsColor,
                symbol = stringResource(id = R.string.parenthesis),
                modifier = Modifier.weight(1f),
                onClick = { calculatorViewModel.parenthesis() }
            )

            ButtonComponent(
                color = OperationButtonsColor,
                symbol = stringResource(id = R.string.percentage),
                modifier = Modifier.weight(1f),
                onClick = { calculatorViewModel.updateOperation('%') }
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
                onClick = { calculatorViewModel.enterNumber('7') }
            )

            ButtonComponent(
                color = NumberButtonsColor,
                symbol = stringResource(id = R.string.eight),
                modifier = Modifier.weight(1f),
                onClick = { calculatorViewModel.enterNumber('8') }
            )

            ButtonComponent(
                color = NumberButtonsColor,
                symbol = stringResource(id = R.string.nine),
                modifier = Modifier.weight(1f),
                onClick = { calculatorViewModel.enterNumber('9') }
            )

            ButtonComponent(
                color = OperationButtonsColor,
                symbol = stringResource(id = R.string.divide),
                modifier = Modifier.weight(1f),
                onClick = { calculatorViewModel.updateOperation('/') }
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
                onClick = { calculatorViewModel.enterNumber('4') }
            )

            ButtonComponent(
                color = NumberButtonsColor,
                symbol = stringResource(id = R.string.five),
                modifier = Modifier.weight(1f),
                onClick = { calculatorViewModel.enterNumber('5') }
            )

            ButtonComponent(
                color = NumberButtonsColor,
                symbol = stringResource(id = R.string.six),
                modifier = Modifier.weight(1f),
                onClick = { calculatorViewModel.enterNumber('6') }
            )

            ButtonComponent(
                color = OperationButtonsColor,
                symbol = stringResource(id = R.string.multiply),
                modifier = Modifier.weight(1f),
                onClick = { calculatorViewModel.updateOperation('x') }
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
                onClick = { calculatorViewModel.enterNumber('1') }
            )

            ButtonComponent(
                color = NumberButtonsColor,
                symbol = stringResource(id = R.string.two),
                modifier = Modifier.weight(1f),
                onClick = { calculatorViewModel.enterNumber('2') }
            )

            ButtonComponent(
                color = NumberButtonsColor,
                symbol = stringResource(id = R.string.three),
                modifier = Modifier.weight(1f),
                onClick = { calculatorViewModel.enterNumber('3') }
            )

            ButtonComponent(
                color = OperationButtonsColor,
                symbol = stringResource(id = R.string.divide),
                modifier = Modifier.weight(1f),
                onClick = { calculatorViewModel.updateOperation('-') }
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
                onClick = { calculatorViewModel.enterNumber('0') }
            )

            ButtonComponent(
                color = NumberButtonsColor,
                symbol = stringResource(id = R.string.dot),
                modifier = Modifier.weight(1f),
                onClick = { calculatorViewModel.updateOperation('.') }
            )

            ButtonComponent(
                color = EqualButtonColor,
                symbol = stringResource(id = R.string.equal),
                modifier = Modifier.weight(1f),
                onClick = { calculatorViewModel.calculateResult() }
            )

            ButtonComponent(
                color = OperationButtonsColor,
                symbol = stringResource(id = R.string.plus),
                modifier = Modifier.weight(1f),
                onClick = { calculatorViewModel.updateOperation('+') }
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