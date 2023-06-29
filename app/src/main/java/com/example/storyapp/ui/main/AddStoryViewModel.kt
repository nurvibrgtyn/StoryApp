package com.example.storyapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.data.model.User
import com.example.storyapp.data.repository.StoryRepository
import com.google.android.gms.maps.model.LatLng
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(private val repository: StoryRepository) : ViewModel() {

    fun addStory(token: String, file: MultipartBody.Part, description: RequestBody, latLng: LatLng?) = repository.addStory(token, file, description, latLng)

    fun getUser(): LiveData<User> {
        return repository.getUserData()
    }
}