package com.example.lablearnandroid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lablearnandroid.utils.PokemonEntry
import com.example.lablearnandroid.utils.PokemonNetwork
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PokemonViewModel : ViewModel() {

    private val _pokemonList = MutableStateFlow<List<PokemonEntry>>(emptyList())
    val pokemonList = _pokemonList.asStateFlow()

    fun fetchPokemon() {
        viewModelScope.launch {
            try {
                val response = PokemonNetwork.api.getKantoPokedex()
                _pokemonList.value = response.pokemon_entries
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}