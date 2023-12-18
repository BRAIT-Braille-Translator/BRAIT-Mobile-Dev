package com.bangkit.braitexample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.braitexample.data.model.User
import com.bangkit.braitexample.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository): ViewModel() {

    fun postLogin(email: String, password: String) = authRepository.postLogin(email,password)

    fun saveSession(user: User) {
        viewModelScope.launch {
            authRepository.saveSession(user)
        }
    }
    fun getSession(): LiveData<User> {
        return authRepository.getSession().asLiveData()
    }
}