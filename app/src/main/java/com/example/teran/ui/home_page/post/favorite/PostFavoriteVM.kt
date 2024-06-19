package com.example.teran.ui.home_page.post.favorite

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teran.data.api.ApiConfig
import com.example.teran.data.api.response.post.DeletePostResponse
import com.example.teran.data.model.Post
import com.example.teran.data.model.PostFavorite
import com.example.teran.data.repository.PostFavoriteRepository
import com.example.teran.data.sharedpref.MySharedPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostFavoriteVM(application: Application) : ViewModel() {

    private val postFavoriteRepository: PostFavoriteRepository = PostFavoriteRepository(application)
    private var sharedPref: MySharedPreferences = MySharedPreferences(application)

    fun insert(postFavorite: PostFavorite) {
        postFavoriteRepository.insert(postFavorite)
    }

    fun getAllPostsFavorites(): LiveData<List<PostFavorite>> = postFavoriteRepository.getAllPostsFavorites()

    fun searchPostFavoriteByID(postID: String): LiveData<PostFavorite> {
        return postFavoriteRepository.search(postID)
    }

    fun addPostToFavorite(postFavorite: PostFavorite) {
        postFavoriteRepository.insert(postFavorite)
    }
    fun deletePostFavoriteByID(postFavorite: PostFavorite) {
        postFavoriteRepository.delete(postFavorite)
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