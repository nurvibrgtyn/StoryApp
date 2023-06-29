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
import com.example.storyapp.databinding.ActivityRegisterBinding
import com.example.storyapp.data.Resource
import com.example.storyapp.util.ViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupViewModel()
        setupAction()
        setAnimation()
    }

    private fun setupViewModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        userViewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]
    }

    private fun setupAction() {
        binding.tvLogin.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.btnSignup.setOnClickListener{
            if (valid()) {
                val name = binding.edRegisterName.text.toString()
                val email = binding.edRegisterEmail.text.toString()
                val password = binding.edRegisterPassword.text.toString()
                userViewModel.userRegister(name, email, password).observe(this) {
                    when (it) {
                        is Resource.Success -> {
                            showLoading(false)
                            Toast.makeText(this, it.data.message, Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, LoginActivity::class.java))
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
    }

    private fun valid() =
        binding.edRegisterEmail.error == null && binding.edRegisterPassword.error == null && binding.edRegisterName.error == null && !binding.edRegisterEmail.text.isNullOrEmpty() && !binding.edRegisterPassword.text.isNullOrEmpty() && !binding.edRegisterName.text.isNullOrEmpty()

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

    private fun showLoading(isLoading: Boolean) = binding.progressBar.isVisible == isLoading
}
