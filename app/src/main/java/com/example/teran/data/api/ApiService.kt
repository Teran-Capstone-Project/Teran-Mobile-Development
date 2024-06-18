package com.example.teran.data.api

import com.example.teran.data.api.request.AddPostRequest
import com.example.teran.data.api.request.LoginRequest
import com.example.teran.data.api.request.RegisterRequest
import com.example.teran.data.api.response.auth.LoginResponse
import com.example.teran.data.api.response.auth.RegisterResponse
import com.example.teran.data.api.response.post.AddPostResponse
import com.example.teran.data.api.response.post.GetAllPostsResponse
import com.example.teran.data.api.response.post.GetPostResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

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

    @GET("posts/{postID}")
    fun getAllPostComments(
        @Header("Authorization") token: String,
        @Path("postID") postId: String
    ) : Call<GetPostResponse>
}