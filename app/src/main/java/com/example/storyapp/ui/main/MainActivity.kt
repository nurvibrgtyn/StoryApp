package com.example.storyapp.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityMainBinding
import com.example.storyapp.data.Resource
import com.example.storyapp.data.preferences.UserPreferences
import com.example.storyapp.ui.user.LoginActivity
import com.example.storyapp.ui.user.LoginViewModel
import com.example.storyapp.util.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")
    private lateinit var binding: ActivityMainBinding
    private lateinit var storyAdapter: StoryAdapter
    private lateinit var mainViewModel: MainViewModel
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupView()
        onClick()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        moveTaskToBack(true)
    }

    private fun onClick() {
        binding.fabAddStory.setOnClickListener{
            startActivity(Intent(this, AddStoryActivity::class.java))
        }
        binding.fabSetting.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                loginViewModel.logout()
                startActivity(Intent(this, LoginActivity::class.java))
                finishAffinity()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupView() {
        storyAdapter = StoryAdapter()

        loginViewModel.getUserToken().observe(this){ token ->
            if (token.isNotEmpty()){
                mainViewModel.stories.observe(this) {
                    when (it) {
                        is Resource.Success -> {
                            it.data?.let { stories -> storyAdapter.setData(stories) }
                            showLoading(false)
                        }
                        is Resource.Loading -> showLoading(true)
                        is Resource.Error -> {
                            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                CoroutineScope(Dispatchers.IO).launch {
                    mainViewModel.getStories()
                }
            }
            else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        with(binding.rvStory) {
            setHasFixedSize(true)
            adapter = storyAdapter
        }
    }

    private fun setupViewModel() {
        val pref = UserPreferences.getInstance(dataStore)
        val viewModelFactory = ViewModelFactory(pref)

        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        loginViewModel= ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]

    }

    private fun showLoading(isLoad: Boolean) {
        if (isLoad){
            binding.progressBar.visibility = View.VISIBLE
        }
        else {
            binding.progressBar.visibility = View.GONE
        }
    }
}