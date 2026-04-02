package com.example.project.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.project.ui.screens.MainScreen
import com.example.project.ui.screens.ResultScreen
import com.example.project.ui.screens.HistoryScreen

@Composable
fun DecidrNavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {

        composable("main") {
            MainScreen(
                onNavigateToResult = {
                    navController.navigate("result")
                },
                onNavigateToHistory = {
                    navController.navigate("history")
                }
            )
        }

        composable("result") {
            ResultScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("history") {
            HistoryScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}