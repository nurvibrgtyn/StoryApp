package com.example.storyapp.ui.user

import android.util.Log
import androidx.lifecycle.*
import com.example.storyapp.data.Resource
import com.example.storyapp.data.remote.ApiInstance
import com.example.storyapp.data.response.BaseResponse
import com.example.storyapp.data.response.LoginResponse
import com.example.storyapp.data.preferences.UserPreferences
import com.example.storyapp.data.request.LoginRequest
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: UserPreferences) : ViewModel() {

    private val _userInfo = MutableLiveData<Resource<String>>()
    val userInfo: LiveData<Resource<String>> = _userInfo

    fun login(email: String, password: String) {
        _userInfo.postValue(Resource.Loading())
        val client = ApiInstance.getApiClient().login(LoginRequest(email, password))

        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()?.loginResult?.token
                    result?.let { saveUserToken(it) }
                    _userInfo.postValue(Resource.Success(result))
                } else {
                    val errorResponse = Gson().fromJson(response.errorBody()?.charStream(),BaseResponse::class.java
                    )
                    _userInfo.postValue(Resource.Error(errorResponse.message))
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e(
                    LoginViewModel::class.java.simpleName, "onFailure login"
                )
                _userInfo.postValue(Resource.Error(t.message))
            }
        })
    }

    fun logout() = clearUserToken()
    fun getUserToken() = pref.getToken().asLiveData()

    private fun saveUserToken(key: String) {
        viewModelScope.launch {
            pref.saveToken(key)
        }
    }

    private fun clearUserToken() {
        viewModelScope.launch {
            pref.clearToken()
        }
    }

}