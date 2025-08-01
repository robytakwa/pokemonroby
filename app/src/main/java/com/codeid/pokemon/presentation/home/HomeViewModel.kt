package com.codeid.pokemon.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeid.pokemon.domain.model.Pokemon
import com.codeid.pokemon.domain.usecase.GetPokemonListUseCase
import com.codeid.pokemon.domain.usecase.SavePokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val savePokemonListUseCase: SavePokemonListUseCase
) : ViewModel() {

    suspend fun getPokemons(offset: Int = 0): List<Pokemon> {
        val pokemons = getPokemonListUseCase(limit = 10, offset = offset)
        viewModelScope.launch { savePokemonListUseCase(pokemons) }
        return pokemons
    }

    fun searchPokemonByName(pokemons: List<Pokemon>, query: String): List<Pokemon> {
        return pokemons.filter { it.name.contains(query, ignoreCase = true) }
    }
}