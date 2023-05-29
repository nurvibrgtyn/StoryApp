package com.example.storyapp.ui.user

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityRegisterBinding
import com.example.storyapp.data.Resource
import com.example.storyapp.data.preferences.UserPreferences
import com.example.storyapp.util.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupView()
        setupAction()
        setAnimation()
    }

    private fun setupViewModel() {
        val pref = UserPreferences.getInstance(dataStore)
        registerViewModel = ViewModelProvider(this, ViewModelFactory(pref))[RegisterViewModel::class.java]
    }

    private fun setupView() {
        registerViewModel.userInfo.observe(this) {
            when (it) {
                is Resource.Success -> {
                    showLoading(false)
                    Toast.makeText(this, it.data, Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finishAffinity()
                }
                is Resource.Loading -> showLoading(true)
                is Resource.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    showLoading(false)
                }
            }
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.tvLogin.setOnClickListener{
            startActivity(
                Intent(
                    this, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }
        binding.btnSignup.setOnClickListener{
            if (valid()) {
                val name = binding.edRegisterName.text.toString()
                val email = binding.edRegisterEmail.text.toString()
                val password = binding.edRegisterPassword.text.toString()
                registerViewModel.register(name, email, password)
            } else {
                Toast.makeText(this, resources.getString(R.string.input_invalid), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setAnimation() {
        val appIcon = ObjectAnimator.ofFloat(binding.icon, View.ALPHA, 1f).setDuration(700)
        val appName = ObjectAnimator.ofFloat(binding.tvAppName, View.ALPHA, 1f).setDuration(700)
        val tvName = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(700)
        val etName = ObjectAnimator.ofFloat(binding.edRegisterName, View.ALPHA, 1f).setDuration(700)
        val tvEmail = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(700)
        val etEmail = ObjectAnimator.ofFloat(binding.edRegisterEmail, View.ALPHA, 1f).setDuration(700)
        val tvPassword = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(700)
        val etPassword = ObjectAnimator.ofFloat(binding.edRegisterPassword, View.ALPHA, 1f).setDuration(700)
        val txtHaveAcc = ObjectAnimator.ofFloat(binding.tvHaveAcc, View.ALPHA, 1f).setDuration(700)
        val txtLogin = ObjectAnimator.ofFloat(binding.tvLogin, View.ALPHA, 1f).setDuration(700)
        val btnSignup = ObjectAnimator.ofFloat(binding.btnSignup, View.ALPHA, 1f).setDuration(700)

        val nameAnimation = AnimatorSet().apply {
            playTogether(tvName, etName)
        }
        val emailAnimation = AnimatorSet().apply {
            playTogether(tvEmail, etEmail)
        }
        val passwordAnimation = AnimatorSet().apply {
            playTogether(tvPassword, etPassword)
        }
        val buttonAnimation = AnimatorSet().apply {
            playTogether(btnSignup, txtHaveAcc, txtLogin,)
        }

        AnimatorSet().apply {
            playSequentially(
                appIcon,
                appName,
                nameAnimation,
                emailAnimation,
                passwordAnimation,
                buttonAnimation
            )
            start()
        }
    }

    private fun showLoading(isLoad: Boolean) {
        if (isLoad){
            binding.progressBar.visibility = View.VISIBLE
        }
        else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun valid() =
        binding.edRegisterEmail.error == null && binding.edRegisterPassword.error == null && binding.edRegisterName.error == null && !binding.edRegisterEmail.text.isNullOrEmpty() && !binding.edRegisterPassword.text.isNullOrEmpty() && !binding.edRegisterName.text.isNullOrEmpty()
}
