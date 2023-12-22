package com.bangkit.braitexample.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bangkit.braitexample.data.model.User
import com.bangkit.braitexample.data.model.UserPreference
import com.bangkit.braitexample.data.response.PredictImageResponse
import com.bangkit.braitexample.data.response.ProfileResponse
import com.bangkit.braitexample.retrofit.ApiService
import com.bangkit.braitexample.data.response.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import okhttp3.MultipartBody

class PredictRepository(
    private val apiService: ApiService,
    private val userPreference: UserPreference) {

    fun getSession(): Flow<User> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    fun predictImage(file : MultipartBody.Part) : LiveData<Result<PredictImageResponse>> = liveData {
        emit(Result.Loading)
        try {
            val user = userPreference.getSession().first()
            val response = apiService.postImage(file)
            Log.d("Token", user.token)
           emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun updateImageProfile(file : MultipartBody.Part) : LiveData<Result<ProfileResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.updateImageProfile(file)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getUser(): LiveData<Result<ProfileResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUser()
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        //fungsi untuk mendapatkan instance
        @Volatile
        private var instance: PredictRepository? = null

        fun getInstance(
            apiService: ApiService, userPreference: UserPreference
        ): PredictRepository = instance ?: PredictRepository(apiService, userPreference)
    }
}