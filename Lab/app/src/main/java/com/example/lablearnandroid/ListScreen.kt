package com.example.lablearnandroid

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ListScreen(viewModel: PokemonViewModel) {

    val pokemonList by viewModel.pokemonList.collectAsState()

    // โหลดข้อมูลตอนเปิดหน้า
    LaunchedEffect(Unit) {
        viewModel.fetchPokemon()
    }

    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(pokemonList) { entry ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "#${entry.entry_number}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = entry.pokemon_species.name,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}