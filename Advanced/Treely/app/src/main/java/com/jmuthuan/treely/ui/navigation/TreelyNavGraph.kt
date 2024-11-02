package com.jmuthuan.treely.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jmuthuan.treely.ui.home.HomeDestination
import com.jmuthuan.treely.ui.home.HomeScreen
import com.jmuthuan.treely.ui.persons.PersonEntryNavigation
import com.jmuthuan.treely.ui.persons.PersonEntryScreen

/**
 * Provides Navigation graph for the application.
 */

@Composable
fun TreelyNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToEntryPerson =  { navController.navigate(PersonEntryNavigation.route) }
            )
        }

        composable(route = PersonEntryNavigation.route) {
            PersonEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp()}
            )
        }

//        composable(route = PersonDetailScreen.routeWithArgs) {
//            PersonDetail()
//        }
//
//        composable(route =  PersonEditScreen.routeWithArgs) {
//            PersonEdit()
//        }
    }

    
}