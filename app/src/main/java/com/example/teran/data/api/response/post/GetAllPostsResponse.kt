package com.example.teran.data.api.response.post

import com.google.gson.annotations.SerializedName

data class GetAllPostsResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("posts")
	val posts: List<PostsItem>
)

data class User(

	@field:SerializedName("profilePicture")
	val profilePicture: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("role")
	val role: String,

	@field:SerializedName("__v")
	val V: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)

data class PostsItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("comments")
	val comments: List<Any>,

	@field:SerializedName("__v")
	val V: Int,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("user")
	val user: User,

	@field:SerializedName("updatedAt")
	val updatedAt: String,

	@field:SerializedName("anonim")
	val anonim: Boolean
)

data class CommentsItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("__v")
	val V: Int,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("user")
	val user: User,

	@field:SerializedName("content")
	val content: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
