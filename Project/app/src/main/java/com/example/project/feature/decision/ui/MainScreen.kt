package com.example.project.feature.decision.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.project.core.ui.DecidrButton
import com.example.project.core.ui.DecidrCard

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
                title = { 
                    Text(
                        "Decidr by sarucha",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ) 
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                actions = {
                    TextButton(onClick = onNavigateToHistory) {
                        Text("History", color = MaterialTheme.colorScheme.secondary)
                    }
                    TextButton(onClick = onNavigateToChat) {
                        Text("Chat", color = MaterialTheme.colorScheme.secondary)
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            
            // Mascot Jelly Area (Styled and approachable)
            DecidrCard(modifier = Modifier.fillMaxWidth().aspectRatio(1.5f)) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "🐶",
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "Hi, I'm Jelly!",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.tertiary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Status: ${uiState.mascotState}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // User Input Card Example
            DecidrCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = "What should we decide?",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = "", 
                        onValueChange = {},
                        placeholder = { Text("E.g., Where to eat?") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.secondary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            if (uiState.isLoading) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.tertiary)
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (uiState.errorMessage != null) {
                Text(text = "Error: ${uiState.errorMessage}", color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Beautiful Bouncy Primary Button
            DecidrButton(
                onClick = { 
                    onAskJellyClicked()
                    onNavigateToResult()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Decide with Jelly!",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
