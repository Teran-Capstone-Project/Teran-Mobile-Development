package com.example.teran.ui.home_page.post

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teran.data.api.ApiConfig
import com.example.teran.data.api.response.post.DeletePostResponse
import com.example.teran.data.api.response.post.GetAllPostsResponse
import com.example.teran.data.api.response.post.PostsItem
import com.example.teran.data.model.PostFavorite
import com.example.teran.data.repository.PostFavoriteRepository
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

    private val _isOnFailure = MutableLiveData<Boolean>()
    val isOnFailure: LiveData<Boolean> = _isOnFailure

    fun getAllPosts(context: Context) {
        _isLoading.value = true
        ApiConfig.getApiService().getAllPosts(
            "Bearer " + sharedPref.getUser().token.toString()
        ).enqueue(object : Callback<GetAllPostsResponse> {
            override fun onResponse(
                call: Call<GetAllPostsResponse>,
                response: Response<GetAllPostsResponse>
            ) {
                _isLoading.value = false
                _isOnFailure.value = false
                val responseBody = response.body()
                if (responseBody != null) {
                    _listPosts.value = responseBody.posts
                }
            }

            override fun onFailure(call: Call<GetAllPostsResponse>, t: Throwable) {
                _isLoading.value = false
                _isOnFailure.value = true
                showToast("Gagal Mencari Post", context)
                showToast("Silahkan Koneksi Ulang", context)
            }
        })
    }

    fun addPostToFavorite(postFavorite: PostFavorite, application: Application) {
        val postFavoriteRepository = PostFavoriteRepository(application)
        postFavoriteRepository.insert(postFavorite)
    }
    fun deletePostFavoriteByID(postFavorite: PostFavorite, application: Application) {
        val postFavoriteRepository = PostFavoriteRepository(application)
        postFavoriteRepository.delete(postFavorite)
    }
    fun searchPostFavoriteByID(postID: String, application: Application): LiveData<PostFavorite> {
        val postFavoriteRepository = PostFavoriteRepository(application)
        return postFavoriteRepository.search(postID)
    }

    fun deletePost(postID: String, context: Context) {
        ApiConfig.getApiService().deletePost(
            "Bearer ${sharedPref.getUser().token}",
            postID
        ).enqueue(object : Callback<DeletePostResponse> {
            override fun onResponse(
                call: Call<DeletePostResponse>,
                response: Response<DeletePostResponse>
            ) {
                if (response.isSuccessful) {
                    getAllPosts(context)
                    showToast("Berhasil Mnghapus Postingan", context)
                }
            }

            override fun onFailure(call: Call<DeletePostResponse>, t: Throwable) {
                showToast("Gagal Mnghapus Postingan", context)
            }

        })
    }

    fun showToast(message: String, context: Context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}