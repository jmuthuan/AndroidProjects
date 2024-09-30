package com.example.mynotks

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mynotks.ui.navigation.MyNotksNavHost

@Composable
fun NotksApp(
    navController: NavHostController = rememberNavController()
) {
    MyNotksNavHost(navController = navController)
}