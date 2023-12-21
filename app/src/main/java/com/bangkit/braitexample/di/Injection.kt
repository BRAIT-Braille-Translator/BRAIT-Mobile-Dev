package com.bangkit.braitexample.di

import android.content.Context
import com.bangkit.braitexample.data.model.UserPreference
import com.bangkit.braitexample.data.model.dataStore
import com.bangkit.braitexample.repository.AuthRepository
import com.bangkit.braitexample.repository.PredictRepository
import com.bangkit.braitexample.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideAuthRepository(context: Context): AuthRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService("")
        return AuthRepository.getInstance(apiService, pref)
    }
    fun providePredictRepository(context: Context): PredictRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return PredictRepository.getInstance(apiService, pref)
    }
}