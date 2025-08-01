package com.codeid.pokemon.presentation.register

import androidx.lifecycle.ViewModel
import com.codeid.pokemon.domain.model.User
import com.codeid.pokemon.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    suspend fun register(user: User) {
        registerUseCase(user)
    }
}
