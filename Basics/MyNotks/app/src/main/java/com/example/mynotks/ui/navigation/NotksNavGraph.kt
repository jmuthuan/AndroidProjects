package com.example.mynotks.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mynotks.ui.home.HomeDestination
import com.example.mynotks.ui.home.MainBackground
import com.example.mynotks.ui.notes.NoteUpdate
import com.example.mynotks.ui.notes.NotesDetail
import com.example.mynotks.ui.notes.NotesDetailDestination
import com.example.mynotks.ui.notes.NotesEntryDestination
import com.example.mynotks.ui.notes.NotesEntryScreen
import com.example.mynotks.ui.notes.NotesUpdateDestination

/**
 * Provides Navigation graph for the application.
 */

@Composable
fun MyNotksNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier,
        ) {
        composable(route = HomeDestination.route) {
            MainBackground(
                navigateToEntryNotes = {
                    navController.navigate("${NotesEntryDestination.route}")},
                navigateToNoteDetails = {
                    navController.navigate( "${NotesDetailDestination.route}/${it}")}
            )
        }
        composable(route = NotesDetailDestination.routeWithArgs) {
            NotesDetail(
                navigateToUpdateScreen = {
                    navController.navigate( route = "${NotesUpdateDestination.route}/${it}")
                },
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }

        composable(route = NotesEntryDestination.route) {
            NotesEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }

        composable(route = NotesUpdateDestination.routeWithArgs) {
            NoteUpdate(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
                navigateToStart = {
                    navController.popBackStack(HomeDestination.route, false)
                }
            )
        }
    }
    
}