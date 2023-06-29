package com.example.storyapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.storyapp.data.preferences.UserPreferences
import com.example.storyapp.data.remote.ApiInstance
import com.example.storyapp.data.repository.StoryRepository

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("storyapp")

object StoryInjection {
    fun provideRepository(context: Context): StoryRepository {
        val preferences = UserPreferences.getInstance(context.dataStore)
        val apiService = ApiInstance.getApiClient()
        return StoryRepository.getInstance(preferences, apiService)
    }
}