package com.jmuthuan.treely.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jmuthuan.treely.ui.navigation.TreelyNavHost

@Composable
fun TreelyApp(
    navController: NavHostController = rememberNavController()
) {
    TreelyNavHost(navController = navController)
}