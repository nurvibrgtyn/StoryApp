package com.example.storyapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.storyapp.data.Resource
import com.example.storyapp.data.StoryPagingSource
import com.example.storyapp.data.model.Story
import com.example.storyapp.data.model.User
import com.example.storyapp.data.preferences.UserPreferences
import com.example.storyapp.data.remote.Api
import com.example.storyapp.data.request.LoginRequest
import com.example.storyapp.data.request.RegisterRequest
import com.example.storyapp.data.response.BaseResponse
import com.example.storyapp.data.response.LoginResponse
import com.example.storyapp.data.response.StoryResponse
import com.google.android.gms.maps.model.LatLng
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.lang.Exception

class StoryRepository(
    private val pref: UserPreferences, private val apiService: Api) {

    fun getStory(): LiveData<PagingData<Story>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService, pref)
            }
        ).liveData
    }

    fun userLogin(email: String, password: String): LiveData<Resource<LoginResponse>> = liveData {
        emit(Resource.Loading)
        try {
            val response = apiService.login(LoginRequest(email, password))
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.d("Login", e.message.toString())
            emit(Resource.Error(e.message.toString()))
        }
    }

    fun userRegister(name: String, email: String, password: String): LiveData<Resource<BaseResponse>> = liveData {
        emit(Resource.Loading)
        try {
            val response = apiService.register(
                RegisterRequest(name, email, password)
            )
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.d("Signup", e.message.toString())
            emit(Resource.Error(e.message.toString()))
        }
    }

    fun addStory(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody,
        latLng: LatLng?,
    ): LiveData<Resource<BaseResponse>> = liveData {
        emit(Resource.Loading)
        try {
            val lat = latLng?.latitude?.toFloat()
            val lng = latLng?.longitude?.toFloat()

            val response = apiService.addStory(token, file, description, lat, lng)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.d("Signup", e.message.toString())
            emit(Resource.Error(e.message.toString()))
        }
    }

    fun getStoryLoc(token: String): LiveData<Resource<StoryResponse>> = liveData {
        emit(Resource.Loading)
        try {
            val response = apiService.getStoryLocation(token, 1)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.d("Signup", e.message.toString())
            emit(Resource.Error(e.message.toString()))
        }
    }

    fun getUserData(): LiveData<User> {
        return pref.getUserData().asLiveData()
    }

    suspend fun saveUserData(user: User) {
        pref.saveUserData(user)
    }

    suspend fun login() {
        pref.login()
    }

    suspend fun logout() {
        pref.logout()
    }

    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(
            preferences: UserPreferences,
            apiService: Api
        ): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(preferences, apiService)
            }.also { instance = it }
    }
}