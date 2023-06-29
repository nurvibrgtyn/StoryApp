package com.example.storyapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyapp.data.model.Story
import com.example.storyapp.data.model.User
import com.example.storyapp.data.repository.StoryRepository

class MainViewModel(private val repository: StoryRepository) : ViewModel(){

    fun getStory(): LiveData<PagingData<Story>> {
        return  repository.getStory().cachedIn(viewModelScope)
    }

    fun getUser(): LiveData<User> {
        return repository.getUserData()
    }
}