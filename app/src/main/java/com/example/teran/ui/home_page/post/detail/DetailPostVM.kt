package com.example.teran.ui.home_page.post.detail

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.teran.data.api.ApiConfig
import com.example.teran.data.api.response.post.GetPostResponse
import com.example.teran.data.sharedpref.MySharedPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPostVM(context: Context) : ViewModel() {

    private val sharedPref: MySharedPreferences = MySharedPreferences(context)

    fun getAllPostComments(postId: String) {
        ApiConfig.getApiService().getAllPostComments(
            "Bearer ${sharedPref.getUser().token}",
            postId
        ).enqueue(object : Callback<GetPostResponse> {
            override fun onResponse(call: Call<GetPostResponse>, response: Response<GetPostResponse>) {
                println(response.body()!!.post.comments)
            }

            override fun onFailure(call: Call<GetPostResponse>, t: Throwable) {
                println(t)
            }
        })
    }
}