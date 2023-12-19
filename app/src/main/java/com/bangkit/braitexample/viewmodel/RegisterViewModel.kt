package com.bangkit.braitexample.viewmodel

import androidx.lifecycle.ViewModel
import com.bangkit.braitexample.repository.AuthRepository

class RegisterViewModel(private val authRepository: AuthRepository): ViewModel() {

    fun postRegister(name: String, email: String, password: String) = authRepository.postRegister(name, email, password)
}