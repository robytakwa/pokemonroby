package com.codeid.pokemon.domain.usecase

import com.codeid.pokemon.domain.model.Pokemon
import com.codeid.pokemon.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(name: String): Pokemon {
        return repository.getPokemonDetail(name)
    }
}
