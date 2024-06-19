package com.example.teran.data.api.request.comment

data class AddCommentRequest(
    val postId: String,
    val content: String
)