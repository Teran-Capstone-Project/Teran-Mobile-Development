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
	val comments: List<Any>,

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
