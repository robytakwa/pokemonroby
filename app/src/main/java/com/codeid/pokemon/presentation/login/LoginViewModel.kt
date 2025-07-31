package com.codeid.pokemon.presentation.auth

import androidx.lifecycle.ViewModel
import com.codeid.pokemon.domain.model.User
import com.codeid.pokemon.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    suspend fun login(email: String, password: String): User? {
        return loginUseCase(email, password)
    }
}