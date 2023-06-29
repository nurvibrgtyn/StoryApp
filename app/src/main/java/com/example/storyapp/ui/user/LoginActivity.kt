package com.example.storyapp.ui.user

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.R
import com.example.storyapp.data.Resource
import com.example.storyapp.databinding.ActivityLoginBinding
import com.example.storyapp.data.model.User
import com.example.storyapp.ui.main.MainActivity
import com.example.storyapp.util.ViewModelFactory

class LoginActivity: AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupViewModel()
        setupAction()
        setAnimation()
    }

    private fun setupAction() {

        binding.tvSignup.setOnClickListener{
            startActivity(
                Intent(
                    this, RegisterActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }

        binding.btnLogin.setOnClickListener {
            if (valid()) {
                val email = binding.edLoginEmail.text.toString()
                val password = binding.edLoginPassword.text.toString()
                userViewModel.userLogin(email, password).observe(this) {
                    when (it) {
                        is Resource.Success -> {
                            showLoading(false)
                            val response = it.data
                            saveUserData(
                                User(
                                    response.loginResult?.name.toString(),
                                    response.loginResult?.token.toString(),
                                    true
                                )
                            )
                            startActivity(Intent(this, MainActivity::class.java))
                            finishAffinity()
                        }
                        is Resource.Loading -> showLoading(true)
                        is Resource.Error -> {
                            Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                            showLoading(false)
                        }
                    }
                }
            } else {
                Toast.makeText(
                    this,
                    resources.getString(R.string.input_invalid),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.tvSignup.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun setAnimation() {
        val appIcon = ObjectAnimator.ofFloat(binding.icon, View.ALPHA, 1f).setDuration(700)
        val appName = ObjectAnimator.ofFloat(binding.tvAppName, View.ALPHA, 1f).setDuration(700)
        val tvEmail = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(700)
        val etEmail = ObjectAnimator.ofFloat(binding.edLoginEmail, View.ALPHA, 1f).setDuration(700)
        val tvPassword = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(700)
        val etPass = ObjectAnimator.ofFloat(binding.edLoginPassword, View.ALPHA, 1f).setDuration(700)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(700)
        val txtHaveAcc = ObjectAnimator.ofFloat(binding.tvNoAcc, View.ALPHA, 1f).setDuration(700)
        val txtSignup = ObjectAnimator.ofFloat(binding.tvSignup, View.ALPHA, 1f).setDuration(700)

        val emailAnimation = AnimatorSet().apply {
            playTogether(tvEmail, etEmail)
        }
        val passwordAnimation = AnimatorSet().apply {
            playTogether(etPass, tvPassword)
        }

        val buttonAnimation = AnimatorSet().apply {
            playTogether(btnLogin, txtHaveAcc, txtSignup,)
        }

        AnimatorSet().apply {
            playSequentially(
                appIcon,
                appName,
                emailAnimation,
                passwordAnimation,
                buttonAnimation
            )
            start()
        }
    }

    private fun saveUserData(user: User) {
        userViewModel.saveUser(user)
    }
    private fun setupViewModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        userViewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]
    }

    private fun showLoading(isLoading: Boolean) = binding.progressBar.isVisible == isLoading

    private fun valid() =
        binding.edLoginEmail.error == null && binding.edLoginPassword.error == null && !binding.edLoginEmail.text.isNullOrEmpty() && !binding.edLoginPassword.text.isNullOrEmpty()

}