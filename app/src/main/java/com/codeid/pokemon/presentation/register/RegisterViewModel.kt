package com.codeid.pokemon.presentation.register

import androidx.lifecycle.ViewModel
import com.codeid.pokemon.domain.model.User
import com.codeid.pokemon.domain.usecase.RegisterUseCase

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    suspend fun register(user: User) {
        registerUseCase(user)
    }
}