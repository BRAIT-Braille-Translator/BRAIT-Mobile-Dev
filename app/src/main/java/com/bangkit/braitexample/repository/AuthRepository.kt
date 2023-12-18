package com.bangkit.braitexample.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bangkit.braitexample.data.model.User
import com.bangkit.braitexample.data.model.UserPreference
import com.bangkit.braitexample.data.request.LoginRequest
import com.bangkit.braitexample.data.request.RegisterRequest
import com.bangkit.braitexample.data.response.LoginResponse
import com.bangkit.braitexample.data.response.RegisterResponse
import com.bangkit.braitexample.retrofit.ApiService
import com.bangkit.braitexample.data.response.Result
import kotlinx.coroutines.flow.Flow

class AuthRepository(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    fun getSession(): Flow<User> {
        return userPreference.getSession()
    }
    suspend fun saveSession(user: User) {
        userPreference.saveSession(user)
    }

     fun postRegister(username: String, email: String, password: String): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val registerRequest = RegisterRequest(username, email, password)
            val response = apiService.postRegister(registerRequest)
            emit(Result.Success(response))
        } catch (e:Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

     fun postLogin(
        email: String,
        password: String
    ): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val loginRequest = LoginRequest(email, password)
            val response = apiService.postLogin(loginRequest)
            emit(Result.Success(response))
        } catch (e:Exception) {
            Log.d("Login", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        fun getInstance(apiService: ApiService, userPreference: UserPreference): AuthRepository {
            return AuthRepository(apiService, userPreference)
        }
    }
}