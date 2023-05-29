package com.example.storyapp.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.data.preferences.UserPreferences
import com.example.storyapp.ui.main.AddStoryViewModel
import com.example.storyapp.ui.main.MainViewModel
import com.example.storyapp.ui.user.LoginViewModel
import com.example.storyapp.ui.user.RegisterViewModel

class ViewModelFactory(private val pref: UserPreferences) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(pref) as T
        }
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(pref) as T
        }
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(pref) as T
        }
        if (modelClass.isAssignableFrom(AddStoryViewModel::class.java)) {
            return AddStoryViewModel(pref) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.simpleName)
    }
}