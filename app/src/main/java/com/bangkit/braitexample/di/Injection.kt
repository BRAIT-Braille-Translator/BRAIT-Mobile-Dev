package com.bangkit.braitexample.di

import android.content.Context
import com.bangkit.braitexample.data.model.UserPreference
import com.bangkit.braitexample.data.model.dataStore
import com.bangkit.braitexample.repository.AuthRepository
import com.bangkit.braitexample.retrofit.ApiConfig

object Injection {
    fun provideAuthRepository(context: Context): AuthRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return AuthRepository.getInstance(apiService, pref)
    }
}