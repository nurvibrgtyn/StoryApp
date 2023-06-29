package com.example.storyapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.storyapp.data.model.Story
import com.example.storyapp.databinding.ActivityDetailStoryBinding
import com.example.storyapp.util.DateFormatter
import java.util.TimeZone

class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding

    companion object{
        const val EXTRA_DETAIL = "extra_detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater )
        setContentView(binding.root)
        supportActionBar?.hide()
        setupView()
    }

    private fun setupView() {
        val detail = intent.getParcelableExtra<Story>(EXTRA_DETAIL)

        binding.apply {
            tvDetailName.text = detail?.name
            tvDetailDescription.text = detail?.description
            tvDate.text = DateFormatter.formatDate(detail?.createdAt, TimeZone.getDefault().id)
            showLoading(false)
        }
        Glide.with(this)
            .load(detail?.photoUrl)
            .into(binding.ivDetailPhoto)
    }

    private fun showLoading(isLoading: Boolean) = binding.progressBar.isVisible == isLoading
}