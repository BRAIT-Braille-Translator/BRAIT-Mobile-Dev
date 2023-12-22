package com.bangkit.braitexample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.braitexample.data.model.User
import com.bangkit.braitexample.repository.AuthRepository

class SplashViewModel(private val authRepository: AuthRepository): ViewModel() {
    fun getSession(): LiveData<User> {
        return authRepository.getSession().asLiveData()
    }
}