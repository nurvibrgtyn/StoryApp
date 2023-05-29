package com.example.storyapp.ui.user

import android.util.Log
import androidx.lifecycle.*
import com.example.storyapp.data.Resource
import com.example.storyapp.data.remote.ApiInstance
import com.example.storyapp.data.response.BaseResponse
import com.example.storyapp.data.preferences.UserPreferences
import com.example.storyapp.data.request.RegisterRequest
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val pref: UserPreferences) : ViewModel() {

    private val _userInfo = MutableLiveData<Resource<String>>()
    val userInfo: LiveData<Resource<String>> = _userInfo

    fun register(name: String, email: String, password: String) {
        _userInfo.postValue(Resource.Loading())
        val client = ApiInstance.getApiClient().register(RegisterRequest(name, email, password))

        client.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.isSuccessful) {
                    val message = response.body()?.message.toString()
                    _userInfo.postValue(Resource.Success(message))
                } else {
                    val errorResponse = Gson().fromJson(
                        response.errorBody()?.charStream(),
                        BaseResponse::class.java
                    )
                    _userInfo.postValue(Resource.Error(errorResponse.message))
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.e(
                    RegisterViewModel::class.java.simpleName, "onFailure register"
                )
                _userInfo.postValue(Resource.Error(t.message))
            }
        })
    }
}