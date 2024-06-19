package com.example.teran.ui.home_page.profile.tab.mypost

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teran.data.api.ApiConfig
import com.example.teran.data.api.response.post.DeletePostResponse
import com.example.teran.data.api.response.post.GetAllPostsByUserIdResponse
import com.example.teran.data.model.Post
import com.example.teran.data.model.PostFavorite
import com.example.teran.data.repository.PostFavoriteRepository
import com.example.teran.data.sharedpref.MySharedPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPostViewModel(val context: Context) : ViewModel() {

    private val sharedPref = MySharedPreferences(context)

    private val _myPosts = MutableLiveData<ArrayList<Post>>()
    val myPosts: LiveData<ArrayList<Post>> = _myPosts

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isOnFailure = MutableLiveData<Boolean>()
    val isOnFailure: LiveData<Boolean> = _isOnFailure

    fun getAllMyPosts() {
        _isLoading.value = true
        ApiConfig.getApiService().getAllPostsByUserID(
            "Bearer ${sharedPref.getUser().token}",
            sharedPref.getUser().id.toString()
        ).enqueue(object : Callback<GetAllPostsByUserIdResponse> {
            override fun onResponse(
                call: Call<GetAllPostsByUserIdResponse>,
                response: Response<GetAllPostsByUserIdResponse>
            ) {
                _isOnFailure.value = false
                _isLoading.value = false
                val responseBody = response.body()
                if (responseBody != null) {
                    val myPostsShelter = ArrayList<Post>()
                    responseBody.posts.forEach {
                        myPostsShelter.add(
                            Post(
                                it.id,
                                it.description,
                                it.user.name,
                                it.user.profilePicture,
                                it.anonim,
                                it.user.id,
                                it.comments.size,
                                it.createdAt
                            )
                        )
                    }
                    _myPosts.value = myPostsShelter
                }
            }

            override fun onFailure(p0: Call<GetAllPostsByUserIdResponse>, p1: Throwable) {
                _isOnFailure.value = true
                _isLoading.value = false
            }

        })
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
                    getAllMyPosts()
                    showToast("Berhasil Mnghapus Postingan", context)
                }
            }

            override fun onFailure(call: Call<DeletePostResponse>, t: Throwable) {
                showToast("Gagal Mnghapus Postingan", context)
            }

        })
    }

    fun searchPostFavoriteByID(postID: String, application: Application): LiveData<PostFavorite> {
        val postFavoriteRepository = PostFavoriteRepository(application)
        return postFavoriteRepository.search(postID)
    }
    fun addPostToFavorite(postFavorite: PostFavorite, application: Application) {
        val postFavoriteRepository = PostFavoriteRepository(application)
        postFavoriteRepository.insert(postFavorite)
    }
    fun deletePostFavoriteByID(postFavorite: PostFavorite, application: Application) {
        val postFavoriteRepository = PostFavoriteRepository(application)
        postFavoriteRepository.delete(postFavorite)
    }

    fun showToast(message: String, context: Context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}