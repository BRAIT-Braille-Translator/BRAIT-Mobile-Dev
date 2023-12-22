package com.bangkit.braitexample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.braitexample.data.model.User
import com.bangkit.braitexample.repository.AuthRepository
import com.bangkit.braitexample.repository.PredictRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class ProfileViewModel(private val predictRepository: PredictRepository): ViewModel() {

    fun logout() {
        viewModelScope.launch {
            predictRepository.logout()
        }
    }

    fun getSession(): LiveData<User> {
        return predictRepository.getSession().asLiveData()
    }

    fun getProfileUser() = predictRepository.getUser()
}