package com.example.project.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(
    onNavigateToResult: () -> Unit,
    onNavigateToHistory: () -> Unit
) {

    var question by remember { mutableStateOf("") }
    var optionText by remember { mutableStateOf("") }
    var options by remember { mutableStateOf(listOf<String>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // 🐶 Jelly
        Text(
            text = "🐶 Jelly: Let's decide together!",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Question
        OutlinedTextField(
            value = question,
            onValueChange = { question = it },
            label = { Text("Your question") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Add option
        Row {
            OutlinedTextField(
                value = optionText,
                onValueChange = { optionText = it },
                label = { Text("Add option") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                if (optionText.isNotBlank()) {
                    options = options + optionText
                    optionText = ""
                }
            }) {
                Text("+")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Option list
        LazyColumn {
            items(options) { option ->
                Text(text = "• $option")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onNavigateToResult,
            enabled = options.size >= 2,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Decide")
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onNavigateToHistory,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("History")
        }
    }
}