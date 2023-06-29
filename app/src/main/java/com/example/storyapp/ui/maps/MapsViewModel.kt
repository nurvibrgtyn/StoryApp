package com.example.storyapp.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.data.model.User
import com.example.storyapp.data.repository.StoryRepository

class MapsViewModel(private val repository: StoryRepository): ViewModel() {

    fun getStoryLoc(token: String) =
        repository.getStoryLoc(token)

    fun getUser(): LiveData<User> {
        return repository.getUserData()
    }
}