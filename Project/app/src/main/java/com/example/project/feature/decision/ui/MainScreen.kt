package com.example.project.feature.decision.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    uiState: DecisionUiState,
    onNavigateToResult: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToChat: () -> Unit,
    onAskJellyClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Decidr by sarucha") },
                actions = {
                    TextButton(onClick = onNavigateToHistory) {
                        Text("History")
                    }
                    TextButton(onClick = onNavigateToChat) {
                        Text("Chat")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Placeholder for Mascot Jelly
            Text(
                text = "🐶 Jelly is ${uiState.mascotState}",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { 
                    onAskJellyClicked()
                    onNavigateToResult()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Decide!")
            }

            if (uiState.isLoading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator()
            }

            if (uiState.errorMessage != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Error: ${uiState.errorMessage}", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
