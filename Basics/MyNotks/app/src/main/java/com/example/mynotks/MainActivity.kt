package com.example.mynotks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.mynotks.ui.home.MainBackground
import com.example.mynotks.ui.TasksComponent
import com.example.mynotks.ui.theme.MyNotksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyNotksTheme {
                // A surface container using the 'background' color from the theme
                Surface(
//                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                     NotksApp()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MyNotksPreview() {
    MyNotksTheme {
        TasksComponent()
    }
}