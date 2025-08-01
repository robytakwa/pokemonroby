package com.codeid.pokemon.presentation.login
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.codeid.pokemon.domain.model.User
import com.codeid.pokemon.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.core.content.edit

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    application: Application
) : AndroidViewModel(application) {

    suspend fun login(email: String, password: String): User? {
        val user = loginUseCase(email, password)
        user?.let {
            val sharedPref = getApplication<Application>().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            sharedPref.edit {
                putString("name", it.username)
                    .putString("email", it.email)
            }
        }
        return user
    }
}
