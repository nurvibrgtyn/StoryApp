package com.example.storyapp.data.remote

import com.example.storyapp.data.request.LoginRequest
import com.example.storyapp.data.request.RegisterRequest
import com.example.storyapp.data.response.BaseResponse
import com.example.storyapp.data.response.LoginResponse
import com.example.storyapp.data.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface Api {

    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest
    ): BaseResponse

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @GET("stories")
    suspend fun getStory(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): StoryResponse

    @GET("stories")
    suspend fun getStoryLocation(
        @Header("Authorization") token: String,
        @Query("location") location : Int = 1,
    ) : StoryResponse

    @Multipart
    @POST("stories")
    suspend fun addStory(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: Float?,
        @Part("lon") lon: Float?,
    ): BaseResponse
}