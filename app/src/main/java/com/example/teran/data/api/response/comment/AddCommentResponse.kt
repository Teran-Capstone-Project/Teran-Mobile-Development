package com.example.teran.data.api.response.comment

import com.google.gson.annotations.SerializedName

data class AddCommentResponse(

	@field:SerializedName("message")
	val message: String
)
