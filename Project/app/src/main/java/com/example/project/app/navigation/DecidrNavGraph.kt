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

import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.project.feature.decision.domain.Decision
import com.example.project.feature.decision.domain.ProsCons
import com.example.project.feature.decision.domain.Recommendation

@Composable
fun DecidrNavGraph() {

    val navController = rememberNavController()
    var currentDecision by remember { mutableStateOf<Decision?>(null) }

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
                onNavigateToResult = { decision ->
                    currentDecision = decision
                    navController.navigate("result")
                },
                onNavigateToHistory = {
                    navController.navigate("history")
                }
            )
        }

        composable("result") {
            val decision = currentDecision
            
            if (decision != null && decision.options.isNotEmpty()) {
                // Generate a quick mock recommendation based on the user's actual first option
                val mockRec = remember(decision) {
                    val recommendedOption = decision.options.first()
                    Recommendation(
                        recommendedOptionId = recommendedOption.id,
                        reasoning = "${recommendedOption.title} is definitely the best choice based on your factors! 🐶",
                        confidenceScore = 0.9f,
                        prosAndCons = decision.options.associate { 
                            it.id to ProsCons(score = (70..95).random(), pros = listOf("Great choice!"), cons = listOf("None")) 
                        }
                    )
                }
                
                ResultScreen(
                    options = decision.options,
                    recommendation = mockRec,
                    onBack = {
                        navController.popBackStack()
                    },
                    onNavigateToChat = {
                        navController.navigate("chat")
                    }
                )
            } else {
                // Fallback
                ResultScreen(
                    onBack = { navController.popBackStack() },
                    onNavigateToChat = { navController.navigate("chat") }
                )
            }
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