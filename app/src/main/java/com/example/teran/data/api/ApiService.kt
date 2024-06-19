package com.example.teran.data.api

import com.example.teran.data.api.request.post.AddPostRequest
import com.example.teran.data.api.request.auth.LoginRequest
import com.example.teran.data.api.request.auth.RegisterRequest
import com.example.teran.data.api.request.comment.AddCommentRequest
import com.example.teran.data.api.request.profile.UpdateProfileRequest
import com.example.teran.data.api.response.auth.LoginResponse
import com.example.teran.data.api.response.auth.RegisterResponse
import com.example.teran.data.api.response.comment.AddCommentResponse
import com.example.teran.data.api.response.comment.DeleteCommentResponse
import com.example.teran.data.api.response.post.AddPostResponse
import com.example.teran.data.api.response.post.DeletePostResponse
import com.example.teran.data.api.response.post.GetAllPostsByUserIdResponse
import com.example.teran.data.api.response.post.GetAllPostsResponse
import com.example.teran.data.api.response.post.GetPostResponse
import com.example.teran.data.api.response.profile.UpdateProfileResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // AUTHENTICATE //
    @POST("auth/login")
    fun authLogin(
        @Body loginRequest: LoginRequest
    ) : Call<LoginResponse>

    @POST("auth/register")
    fun authRegister(
        @Body registerRequest: RegisterRequest
    ) : Call<RegisterResponse>

    // POST //
    @GET("posts")
    fun getAllPosts(
        @Header("Authorization") token: String
    ) : Call<GetAllPostsResponse>

    @POST("posts")
    fun addPost(
        @Header("Authorization") token: String,
        @Body addPostRequest: AddPostRequest
    ) : Call<AddPostResponse>

    @DELETE("posts/{postID}")
    fun deletePost(
        @Header("Authorization") token: String,
        @Path("postID") postId: String
    ) : Call<DeletePostResponse>

    @GET("posts/{postID}")
    fun getAllPostComments(
        @Header("Authorization") token: String,
        @Path("postID") postId: String
    ) : Call<GetPostResponse>

    @GET("posts")
    fun getAllPostsByUserID(
        @Header("Authorization") token: String,
        @Query("user") userID: String
    ) : Call<GetAllPostsByUserIdResponse>

    // COMMENTS
    @POST("comments")
    fun addCommentPost(
        @Header("Authorization") token: String,
        @Body addCommentRequest: AddCommentRequest
    ) : Call<AddCommentResponse>

    @DELETE("comments/{commentID}")
    fun deleteCommentPost(
        @Header("Authorization") token: String,
        @Path("commentID") commentId: String
    ) : Call<DeleteCommentResponse>

    // PROFILE
    @PUT("profile")
    fun updateProfile(
        @Header("Authorization") token: String,
        @Body updateProfileRequest: UpdateProfileRequest
    ) : Call<UpdateProfileResponse>
}