package com.example.teran.ui.home_page.post.detail

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teran.data.api.ApiConfig
import com.example.teran.data.api.request.comment.AddCommentRequest
import com.example.teran.data.api.response.comment.AddCommentResponse
import com.example.teran.data.api.response.comment.DeleteCommentResponse
import com.example.teran.data.api.response.post.DeletePostResponse
import com.example.teran.data.api.response.post.GetPostResponse
import com.example.teran.data.model.PostComment
import com.example.teran.data.model.PostFavorite
import com.example.teran.data.repository.PostFavoriteRepository
import com.example.teran.data.sharedpref.MySharedPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPostVM(context: Context) : ViewModel() {

    private val sharedPref: MySharedPreferences = MySharedPreferences(context)

    private val _listPostComments = MutableLiveData<List<PostComment>>()
    var listPostComments: LiveData<List<PostComment>> = _listPostComments

    fun getAllPostComments(postId: String) {
        ApiConfig.getApiService().getAllPostComments(
            "Bearer ${sharedPref.getUser().token}",
            postId
        ).enqueue(object : Callback<GetPostResponse> {
            override fun onResponse(call: Call<GetPostResponse>, response: Response<GetPostResponse>) {
                val responseBody = response.body()
                if (responseBody != null) {
                    var postComment = ArrayList<PostComment>()
                    responseBody.post.comments.forEach {
                        postComment.add(
                            PostComment(
                                it.id,
                                it.user.id,
                                it.user.name,
                                it.content,
                                it.user.profilePicture,
                                it.createdAt
                            )
                        )
                    }
                    _listPostComments.value = postComment
                }
            }

            override fun onFailure(call: Call<GetPostResponse>, t: Throwable) {
                println(t.message)
            }
        })
    }

    fun addCommentPost(postId: String, content: String) {
        ApiConfig.getApiService().addCommentPost(
            "Bearer ${sharedPref.getUser().token}",
            AddCommentRequest(
                postId,
                content
            )
        ).enqueue(object : Callback<AddCommentResponse> {
            override fun onResponse(
                call: Call<AddCommentResponse>,
                response: Response<AddCommentResponse>
            ) {
                if (response.isSuccessful) {
                    getAllPostComments(postId)
                }
            }

            override fun onFailure(call: Call<AddCommentResponse>, t: Throwable) {
                println(t.message)
            }

        })
    }

    fun deleteComment(commentID: String, postID: String) {
        ApiConfig.getApiService().deleteCommentPost(
            "Bearer ${sharedPref.getUser().token}",
            commentID
        ).enqueue(object : Callback<DeleteCommentResponse> {
            override fun onResponse(
                call: Call<DeleteCommentResponse>,
                response: Response<DeleteCommentResponse>
            ) {
                if (response.isSuccessful) {
                    getAllPostComments(postID)
                }
            }

            override fun onFailure(p0: Call<DeleteCommentResponse>, t: Throwable) {
                println(t.message)
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
                    // Do nothing
                    showToast("Berhasil Menghapus Postingan", context)
                }
            }

            override fun onFailure(call: Call<DeletePostResponse>, t: Throwable) {
                showToast("Gagal Menghapus Postingan", context)
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