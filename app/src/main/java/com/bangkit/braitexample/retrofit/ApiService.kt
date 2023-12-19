package com.bangkit.braitexample.retrofit

import com.bangkit.braitexample.data.request.LoginRequest
import com.bangkit.braitexample.data.request.RegisterRequest
import com.bangkit.braitexample.data.response.LoginResponse
import com.bangkit.braitexample.data.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/auth/signup")
    suspend fun postRegister(
        @Body registerRequest: RegisterRequest
    ): RegisterResponse

    @POST("/auth/login")
    suspend fun postLogin(
        @Body loginRequest: LoginRequest
    ): LoginResponse
}