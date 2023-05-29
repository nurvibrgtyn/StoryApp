package com.example.storyapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.data.Resource
import com.example.storyapp.data.model.Story
import com.example.storyapp.data.remote.ApiInstance
import com.example.storyapp.data.response.BaseResponse
import com.example.storyapp.data.response.StoryResponse
import com.example.storyapp.data.preferences.UserPreferences
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: UserPreferences) : ViewModel(){
    private val _stories = MutableLiveData<Resource<ArrayList<Story>>>()
    val stories: LiveData<Resource<ArrayList<Story>>> = _stories

    suspend fun getStories() {
        _stories.postValue(Resource.Loading())
        val client =
            ApiInstance.getApiClient().getStories(token = "Bearer ${pref.getToken().first()}")

        client.enqueue(object : Callback<StoryResponse> {
            override fun onResponse(call: Call<StoryResponse>, response: Response<StoryResponse>) {

                if (response.isSuccessful) {
                    response.body()?.let {
                        val listStory = it.listStory
                        _stories.postValue(Resource.Success(ArrayList(listStory)))
                    }
                } else {
                    val errorResponse = Gson().fromJson(
                        response.errorBody()?.charStream(),
                        BaseResponse::class.java
                    )
                    _stories.postValue(Resource.Error(errorResponse.message))
                }
            }

            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                Log.e(
                    MainViewModel::class.java.simpleName,
                    "onFailure getStories"
                )
                _stories.postValue(Resource.Error(t.message))
            }
        })
    }
}