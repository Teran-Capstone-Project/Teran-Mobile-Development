package com.example.teran.data.model

import android.os.Parcelable
import com.example.teran.data.api.response.post.CommentsItem
import com.example.teran.data.api.response.post.User
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    var id: String,
    var desc: String,
    var username: String,
    var profilePictureURL: String,
    var isAnonim: Boolean,
    var userID: String,
    var commentsSize: Int,
    var date: String
) : Parcelable