package com.example.simplecalculator.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simplecalculator.R
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme

@Composable
fun CalculatorScreen(calculatorViewModel: CalculatorViewModel = viewModel()) {
    val calculatorUiState by calculatorViewModel.uiState.collectAsState()

    Column {
        DisplayContainer(calculatorUiState)
        ButtonsContainer(calculatorViewModel)
//        Text(text = calculatorUiState.result)
//        Text(text = calculatorUiState.currentOperation)
//        Row {
//            Button(onClick = { calculatorViewModel.clearDisplay() }) {
//                Text(text = "AC")
//            }
//            Button(onClick = { calculatorViewModel.updateOperation("1") }) {
//                Text(text = "1")
//            }
//            Button(onClick = { calculatorViewModel.calculateResult() }) {
//                Text(text = "=")
//            }
//        }


    }
}


@Composable
fun DisplayContainer(calculatorUiState: CalculatorUiState, modifier: Modifier = Modifier) {

    Column (horizontalAlignment = Alignment.Start) {
        Text(text = calculatorUiState.result)
        Text(text = calculatorUiState.currentOperation)
    }
}

@Composable
fun ButtonsContainer(calculatorViewModel: CalculatorViewModel, modifier: Modifier = Modifier) {

    Column {
        Miscelaneous(calculatorViewModel)
        Row {
            NumbersContainer(calculatorViewModel)
            OperationContainer(calculatorViewModel)
        }
    }
}

@Composable
fun NumbersContainer(calculatorViewModel: CalculatorViewModel, modifier: Modifier = Modifier) {
    Column() {
        Row {
            Button(onClick = {calculatorViewModel.enterNumber('7') } ) {
                Text(text = stringResource(id = R.string.seven))
            }
            Button(onClick = { calculatorViewModel.enterNumber('8') } ) {
                Text(text = stringResource(id = R.string.eight))
            }
            Button(onClick = { calculatorViewModel.enterNumber('9') } ) {
                Text(text = stringResource(id = R.string.nine))
            }
        }

        Row {
            Button(onClick = { calculatorViewModel.enterNumber('4') }) {
                Text(text = stringResource(id = R.string.four))
            }
            Button(onClick = { calculatorViewModel.enterNumber('5') }) {
                Text(text = stringResource(id = R.string.five))
            }
            Button(onClick = { calculatorViewModel.enterNumber('6') }) {
                Text(text = stringResource(id = R.string.six))
            }
        }

        Row {
            Button(onClick = { calculatorViewModel.enterNumber('1') }) {
                Text(text = stringResource(id = R.string.one))
            }
            Button(onClick = { calculatorViewModel.enterNumber('2') }) {
                Text(text = stringResource(id = R.string.two))
            }
            Button(onClick = { calculatorViewModel.enterNumber('3') }) {
                Text(text = stringResource(id = R.string.three))
            }
        }

        Row {
            Button(onClick = { calculatorViewModel.enterNumber('0') }) {
                Text(text = stringResource(id = R.string.zero))
            }
            Button(onClick = { calculatorViewModel.updateOperation('.') }) {
                Text(text = stringResource(id = R.string.dot))
            }
            Button(onClick = { calculatorViewModel.calculateResult() }) {
                Text(text = stringResource(id = R.string.equal))
            }
        }
    }
}

@Composable
fun OperationContainer(calculatorViewModel: CalculatorViewModel, modifier: Modifier = Modifier) {
    Column() {
        Button(onClick = { calculatorViewModel.updateOperation('/') }) {
            Text(text = stringResource(id = R.string.divide))
        }
        Button(onClick = { calculatorViewModel.updateOperation('x') }) {
            Text(text = stringResource(id = R.string.multiply))
        }
        Button(onClick = { calculatorViewModel.updateOperation('-') }) {
            Text(text = stringResource(id = R.string.minus))
        }
        Button(onClick = { calculatorViewModel.updateOperation('+') }) {
            Text(text = stringResource(id = R.string.plus))
        }
    }
}

@Composable
fun Miscelaneous(calculatorViewModel: CalculatorViewModel, modifier: Modifier = Modifier) {
    Row {
        Button(onClick = { calculatorViewModel.clearDisplay() }) {
            Text(text = stringResource(id = R.string.clear))
        }
        Button(onClick = { calculatorViewModel.backspace() }) {
            Image(
                painter = painterResource(id = R.drawable.baseline_backspace_24),
                contentDescription = "erase last operation"
            )
        }
        Button(onClick = { calculatorViewModel.parenthesis() }) {
            Text(text = stringResource(id = R.string.parenthesis))
        }
        Button(onClick = { calculatorViewModel.updateOperation('%') }) {
            Text(text = stringResource(id = R.string.percentage))
        }
    }
}



@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    SimpleCalculatorTheme {
        CalculatorScreen()
    }
}