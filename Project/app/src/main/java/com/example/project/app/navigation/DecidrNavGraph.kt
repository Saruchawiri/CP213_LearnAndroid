package com.example.project.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.project.feature.chat.ui.ChatScreen
import com.example.project.feature.decision.ui.DecisionProcessViewModel
import com.example.project.feature.decision.ui.MainScreen
import com.example.project.feature.decision.ui.ResultScreen
import com.example.project.feature.history.ui.HistoryScreen

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Result : Screen("result")
    object History : Screen("history")
    object Chat : Screen("chat")
}

@Composable
fun DecidrNavGraph(
    navController: NavHostController = rememberNavController(),
    decisionViewModel: DecisionProcessViewModel // Note: In production use Koin/Hilt injected ViewModel
) {
    NavHost(navController = navController, startDestination = Screen.Main.route) {
        
        composable(Screen.Main.route) {
            val uiState by decisionViewModel.uiState.collectAsState()
            MainScreen(
                uiState = uiState,
                onNavigateToResult = { navController.navigate(Screen.Result.route) },
                onNavigateToHistory = { navController.navigate(Screen.History.route) },
                onNavigateToChat = { navController.navigate(Screen.Chat.route) },
                onAskJellyClicked = { decisionViewModel.requestRecommendation() }
            )
        }

        composable(Screen.Result.route) {
            val uiState by decisionViewModel.uiState.collectAsState()
            ResultScreen(
                uiState = uiState,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.History.route) {
            HistoryScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Chat.route) {
            ChatScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
