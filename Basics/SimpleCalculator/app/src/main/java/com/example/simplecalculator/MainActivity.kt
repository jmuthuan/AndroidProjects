package com.example.simplecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.simplecalculator.ui.CalculatorScreen
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalculatorScreen()
                }
            }
        }
    }
}



//@Composable
//fun Calculator() {
//    var operation by remember { mutableStateOf("+-*/") }
//
//    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//        DisplayContainer(operation)
////        Button(onClick = {
////            operation = "1"
////        }) {
////            Text(text = "1")
////        }
////        ButtonsContainer(operation)
//        ButtonCalculator(text = stringResource(id = R.string.seven)) { operation = it }
//    }
//}
//
//@Composable
//fun ButtonCalculator(text: String, onClick: (String) -> Unit) {
//    var currentValue by remember {
//        mutableStateOf(R.string.seven)
//    }
//
//    Button(onClick = { onClick }) {
//        Text(text = "test")
//    }
//}
//
//

//

//



//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    SimpleCalculatorTheme {
//        Calculator()
//    }
//}