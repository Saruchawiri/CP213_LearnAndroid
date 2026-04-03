package com.example.project.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.project.ui.screens.MainScreen
import com.example.project.ui.screens.ResultScreen
import com.example.project.ui.screens.HistoryScreen
import com.example.project.feature.chat.ui.ChatScreen

@Composable
fun DecidrNavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "main",
        enterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(500)) + fadeIn(tween(500))
        },
        exitTransition = {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(500)) + fadeOut(tween(500))
        },
        popEnterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(500)) + fadeIn(tween(500))
        },
        popExitTransition = {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(500)) + fadeOut(tween(500))
        }
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
                },
                // Pass a callback to navigate to Chat. In real app, pass Decision ID.
                onNavigateToChat = {
                    navController.navigate("chat")
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

        composable("chat") {
            ChatScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}