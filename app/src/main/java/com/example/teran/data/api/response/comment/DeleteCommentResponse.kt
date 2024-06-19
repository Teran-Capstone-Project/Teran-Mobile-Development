package com.example.teran.data.api.response.comment

import com.google.gson.annotations.SerializedName

data class DeleteCommentResponse(

	@field:SerializedName("message")
	val message: String
)
