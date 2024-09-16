package com.example.simplecalculator.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme

@Composable
fun CalculatorScreen(
    modifier: Modifier = Modifier,
    calculatorViewModel: CalculatorViewModel = viewModel()
    ) {
    val calculatorUiState by calculatorViewModel.uiState.collectAsState()

    Box(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 32.dp)) {
        Column(modifier = Modifier
            .fillMaxSize()
            .align(Alignment.BottomCenter)
        ) {
            InputDisplayComponent(
                result = calculatorUiState.result,
                operation = calculatorUiState.currentOperation,
                fontSizeState = calculatorUiState.currentOperationFontSize,
                autoResize = { calculatorViewModel.resizeCurrentResultFontSize() }
            )
            Spacer(modifier = Modifier
                .height(8.dp)
            )
            InputButtonsComponent(
                calculatorViewModel = calculatorViewModel,
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .fillMaxWidth()
            )
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