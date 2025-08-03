package com.codeid.pokemon.presentation.login

import android.content.Context
import androidx.lifecycle.ViewModel
import com.codeid.pokemon.domain.model.User
import com.codeid.pokemon.domain.usecase.LoginUseCase
import androidx.core.content.edit

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {
    suspend fun login(context: Context, email: String, password: String): User? {
        val user = loginUseCase(email, password)
        user?.let {
            val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            sharedPref.edit { putString("name", it.username).putString("email", it.email).apply() }
        }
        return user
    }
}
