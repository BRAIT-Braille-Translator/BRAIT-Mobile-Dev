package com.bangkit.braitexample.data.model

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreference(private val dataStore: DataStore<Preferences>) {

    suspend fun saveSession(user: User) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = user.token
        }
        Log.d("DataStore", "Nilai disimpan: ${user.token}")
    }

    fun getSession(): Flow<User> {
        return dataStore.data.map { preferences ->
            User(
                preferences[TOKEN_KEY] ?: "",
            )
        }
    }
    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null
        private val TOKEN_KEY = stringPreferencesKey("token")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val newInstance = UserPreference(dataStore)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}