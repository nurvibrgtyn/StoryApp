package com.example.storyapp.utils

import com.example.storyapp.data.model.Login
import com.example.storyapp.data.model.Story
import com.example.storyapp.data.response.BaseResponse
import com.example.storyapp.data.response.LoginResponse
import com.example.storyapp.data.response.StoryResponse

object DataDummy {

    fun generateDummyStory(): List<Story>{
        val item = arrayListOf<Story>()

        for (i in 0 .. 100){
            val story = Story(
                "id + $i",
                "name + + $i",
                "description + $i",
                "photoUrl + $i",
                "createdAt + $i",
                -6.1335033,
                106.64356
            )
            item.add(story)
        }
        return item
    }
}