package com.bangkit.braitexample.retrofit

import com.bangkit.braitexample.data.request.LoginRequest
import com.bangkit.braitexample.data.request.RegisterRequest
import com.bangkit.braitexample.data.response.LoginResponse
import com.bangkit.braitexample.data.response.PredictImageResponse
import com.bangkit.braitexample.data.response.ProfileResponse
import com.bangkit.braitexample.data.response.RegisterResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @POST("/auth/signup")
    suspend fun postRegister(
        @Body registerRequest: RegisterRequest
    ): RegisterResponse

    @POST("/auth/login")
    suspend fun postLogin(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @Multipart
    @POST("/predict/image")
    suspend fun postImage(
        @Part file: MultipartBody.Part,
    ): PredictImageResponse

    @GET("/user")
    suspend fun getUser():ProfileResponse

    @Multipart
    @POST("/user/profile")
    suspend fun updateImageProfile(
        @Part file: MultipartBody.Part
    ): ProfileResponse
}