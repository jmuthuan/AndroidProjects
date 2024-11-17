package com.jmuthuan.treely.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jmuthuan.treely.ui.home.HomeDestination
import com.jmuthuan.treely.ui.home.HomeScreen
import com.jmuthuan.treely.ui.persons.PersonDetailsNavigation
import com.jmuthuan.treely.ui.persons.PersonEditNavigation
import com.jmuthuan.treely.ui.persons.PersonEditOrDetailScreen
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
                navigateToEntryPerson =  { navController.navigate(PersonEntryNavigation.route) },
                navigateToEditPerson = {
                    navController.navigate("${PersonEditNavigation.route}/${it}")},
                navigateToDetailPerson = {
                    navController.navigate("${PersonDetailsNavigation.route}/${it}")
                }

            )
        }

        composable(route = PersonEntryNavigation.route) {
            PersonEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp()}
            )
        }

        composable(route = PersonEditNavigation.routeWithArgs) {
            PersonEditOrDetailScreen(
                isEdit = true,
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }

        composable(route = PersonDetailsNavigation.routeWithArgs) {
            PersonEditOrDetailScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
                isEdit = false
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