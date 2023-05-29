package com.example.storyapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.storyapp.data.model.Story
import com.example.storyapp.databinding.ActivityDetailStoryBinding

class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding

    companion object{
        const val EXTRA_DETAIL = "extra_detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater )
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {
        val detail = intent.getParcelableExtra<Story>(EXTRA_DETAIL)

        binding.apply {
            tvDetailName.text = detail?.name
            tvDetailDescription.text = detail?.description
            tvDate.text = detail?.createdAt
        }
        Glide.with(this)
            .load(detail?.photoUrl)
            .into(binding.ivDetailPhoto)
    }
}