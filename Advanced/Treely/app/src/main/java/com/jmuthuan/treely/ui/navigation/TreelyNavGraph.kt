package com.jmuthuan.treely.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.android.gms.common.util.CollectionUtils.listOf
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

@RequiresApi(Build.VERSION_CODES.P)
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
                },
                navigateToEntryRelationship = { id, relationship ->
                    navController.navigate(
                        "${PersonEntryNavigation.route}?personId=${id}/relationship=${relationship}")
                }
            )
        }

        composable(route = PersonEntryNavigation.route) {
            PersonEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp()}
            )
        }

        composable(route = "${PersonEntryNavigation.route}?personId={personId}/relationship={relationship}",
            arguments = listOf(
                navArgument(name = "personId") {
                    type = NavType.StringType
                },
                navArgument(name = "relationship"){
                    type = NavType.StringType
                }
            )
        ) {backStackEntry ->
            val personId = backStackEntry.arguments?.getString("personId")
            val relationship = backStackEntry.arguments?.getString("relationship")

            PersonEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
                personId = personId,
                relationship = relationship
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