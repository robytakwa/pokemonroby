package com.codeid.pokemon.presentation.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codeid.pokemon.di.Injector
import com.codeid.pokemon.presentation.detail.DetailViewModel
import com.codeid.pokemon.presentation.home.HomeViewModel
import com.codeid.pokemon.presentation.login.LoginViewModel
import com.codeid.pokemon.presentation.register.RegisterViewModel

class ViewModelFactory private constructor(
    private val loginViewModel: LoginViewModel? = null,
    private val registerViewModel: RegisterViewModel? = null,
    private val homeViewModel: HomeViewModel? = null,
    private val detailViewModel: DetailViewModel? = null
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            loginViewModel != null && modelClass.isAssignableFrom(LoginViewModel::class.java) -> loginViewModel as T
            registerViewModel != null && modelClass.isAssignableFrom(RegisterViewModel::class.java) -> registerViewModel as T
            homeViewModel != null && modelClass.isAssignableFrom(HomeViewModel::class.java) -> homeViewModel as T
            detailViewModel != null && modelClass.isAssignableFrom(DetailViewModel::class.java) -> detailViewModel as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.simpleName}")
        }
    }

    companion object {
        fun inject(context: Context): ViewModelFactory {
            val repository = Injector.provideRepository(context)

            return ViewModelFactory(
                loginViewModel = LoginViewModel(Injector.provideLoginUseCase(context)),
                registerViewModel = RegisterViewModel(Injector.provideRegisterUseCase(context)),
                homeViewModel = HomeViewModel(
                    Injector.provideGetPokemonListUseCase(context),
                    Injector.provideSavePokemonListUseCase(context)
                ),
                detailViewModel = DetailViewModel(Injector.provideDetailUseCase(context))
            )
        }
    }
}
