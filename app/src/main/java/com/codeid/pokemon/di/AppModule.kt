package com.codeid.pokemon.di

import com.codeid.pokemon.data.remote.PokemonApiService
import com.codeid.pokemon.data.remote.PokemonRemoteDataSource
import com.codeid.pokemon.data.repository.PokemonRepositoryImpl
import com.codeid.pokemon.domain.repository.PokemonRepository
import com.codeid.pokemon.domain.usecase.GetPokemonDetailUseCase
import com.codeid.pokemon.domain.usecase.GetPokemonListUseCase
import com.codeid.pokemon.domain.usecase.LoginUseCase
import com.codeid.pokemon.domain.usecase.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(): PokemonApiService {
        return RetrofitProvider.provideRetrofit()
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(api: PokemonApiService): PokemonRemoteDataSource {
        return PokemonRemoteDataSource(api)
    }

    @Provides
    @Singleton
    fun providePokemonRepository(
        remoteDataSource: PokemonRemoteDataSource
    ): PokemonRepository {
        return PokemonRepositoryImpl(remoteDataSource)
    }

    @Provides
    fun provideGetPokemonListUseCase(
        repository: PokemonRepository
    ): GetPokemonListUseCase {
        return GetPokemonListUseCase(repository)
    }

    @Provides
    fun provideGetPokemonDetailUseCase(
        repository: PokemonRepository
    ): GetPokemonDetailUseCase {
        return GetPokemonDetailUseCase(repository)
    }

    @Provides
    fun provideLoginUseCase(
        repository: PokemonRepository
    ): LoginUseCase {
        return LoginUseCase(repository)
    }

    @Provides
    fun provideRegisterUseCase(
        repository: PokemonRepository
    ): RegisterUseCase {
        return RegisterUseCase(repository)
    }
}