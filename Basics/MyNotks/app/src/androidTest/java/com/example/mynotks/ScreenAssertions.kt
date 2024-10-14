package com.example.mynotks

import androidx.navigation.NavController
import org.junit.Assert.assertEquals

fun NavController.assertCurrentRouteName(expectedRoutename: String) {
    assertEquals(expectedRoutename, currentBackStackEntry?.destination?.route)
}