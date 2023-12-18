package com.bangkit.braitexample.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.braitexample.di.Injection
import com.bangkit.braitexample.repository.AuthRepository

class ViewModelFactory private constructor(
    private val authRepository: AuthRepository
):
    ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(authRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class:  $modelClass")
        }
    }
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: ViewModelFactory(
                Injection.provideAuthRepository(context)
            )
    }
}