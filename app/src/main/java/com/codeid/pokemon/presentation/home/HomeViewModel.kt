package com.codeid.pokemon.presentation.home

import androidx.lifecycle.ViewModel
import com.codeid.pokemon.domain.model.Pokemon
import com.codeid.pokemon.domain.usecase.GetPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase
) : ViewModel() {
    suspend fun getPokemons(offset: Int = 0): List<Pokemon> {
        return getPokemonListUseCase(limit = 10, offset = offset)
    }
}