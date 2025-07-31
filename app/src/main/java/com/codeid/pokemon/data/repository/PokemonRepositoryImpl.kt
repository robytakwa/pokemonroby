package com.codeid.pokemon.data.repository

import com.codeid.pokemon.data.remote.PokemonRemoteDataSource
import com.codeid.pokemon.domain.model.Pokemon
import com.codeid.pokemon.domain.model.User
import com.codeid.pokemon.domain.repository.PokemonRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepositoryImpl @Inject constructor(
    private val remoteDataSource: PokemonRemoteDataSource
) : PokemonRepository {

    override suspend fun getPokemonList(limit: Int, offset: Int): List<Pokemon> {
        return remoteDataSource.fetchPokemonList(limit, offset)
    }

    override suspend fun getPokemonDetail(name: String): Pokemon {
        return remoteDataSource.fetchPokemonDetail(name)
    }

    override suspend fun searchPokemon(name: String): Pokemon {
        TODO("Not yet implemented")
    }

    override suspend fun registerUser(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun loginUser(email: String, password: String): User? {
        TODO("Not yet implemented")
    }

    override suspend fun getUserByEmail(email: String): User? {
        TODO("Not yet implemented")
    }


}