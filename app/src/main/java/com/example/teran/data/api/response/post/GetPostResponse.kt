package com.example.teran.data.api.response.post

import com.google.gson.annotations.SerializedName

data class GetPostResponse(

	@field:SerializedName("post")
	val post: Post,

	@field:SerializedName("message")
	val message: String
)

data class Post(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("comments")
	val comments: List<CommentsItem>,

	@field:SerializedName("__v")
	val V: Int,

	@field:SerializedName("anonim")
	val anonim: Boolean,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("user")
	val user: User,

	@field:SerializedName("updatedAt")
	val updatedAt: String
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
