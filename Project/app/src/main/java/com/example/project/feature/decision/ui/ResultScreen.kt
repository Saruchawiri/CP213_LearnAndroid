package com.example.project.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ResultScreen(
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "🐶 Jelly: I think this is the best choice!",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text("Result: (Coming soon AI 🔥)")

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = onBack) {
            Text("Back")
        }
    }
}