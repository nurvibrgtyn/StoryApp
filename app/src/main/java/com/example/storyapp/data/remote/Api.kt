package com.example.storyapp.data.remote

import com.example.storyapp.data.request.LoginRequest
import com.example.storyapp.data.request.RegisterRequest
import com.example.storyapp.data.response.BaseResponse
import com.example.storyapp.data.response.LoginResponse
import com.example.storyapp.data.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @POST("register")
    fun register(
        @Body request: RegisterRequest
    ): Call<BaseResponse>

    @POST("login")
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    @GET("stories")
    fun getStories(
        @Header("Authorization") token: String,
    ): Call<StoryResponse>

    @Multipart
    @POST("stories")
    fun addStory(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<BaseResponse>
}