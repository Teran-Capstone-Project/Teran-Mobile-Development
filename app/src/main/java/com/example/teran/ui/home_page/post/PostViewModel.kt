package com.example.teran.ui.home_page.post

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teran.data.api.ApiConfig
import com.example.teran.data.api.response.post.GetAllPostsResponse
import com.example.teran.data.api.response.post.PostsItem
import com.example.teran.data.sharedpref.MySharedPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostViewModel(context: Context) : ViewModel() {

    private var sharedPref: MySharedPreferences = MySharedPreferences(context)

    private val _listPosts = MutableLiveData<List<PostsItem>>()
    var listPost: LiveData<List<PostsItem>> = _listPosts

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllPosts() {
        _isLoading.value = true
        ApiConfig.getApiService().getAllPosts(
            "Bearer " + sharedPref.getUser().token.toString()
        ).enqueue(object : Callback<GetAllPostsResponse> {
            override fun onResponse(
                call: Call<GetAllPostsResponse>,
                response: Response<GetAllPostsResponse>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (responseBody != null) {
                    println(responseBody)
                    _listPosts.value = responseBody.posts
                }
            }

            override fun onFailure(call: Call<GetAllPostsResponse>, t: Throwable) {
                _isLoading.value = false
                println("onFailure: ${t.message}")
            }
        })
    }

}