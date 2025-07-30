package com.codeid.pokemon.data.remote.model

data class PokemonListResponse(
    val results: List<PokemonResult>
)

data class PokemonResult(
    val name: String,
    val url: String
)

data class PokemonDetailResponse(
    val name: String,
    val abilities: List<PokemonAbility>,
    val sprites: PokemonSprites
)

data class PokemonAbility(
    val ability: AbilityName
)

data class AbilityName(
    val name: String
)

data class PokemonSprites(
    val front_default: String?
)
