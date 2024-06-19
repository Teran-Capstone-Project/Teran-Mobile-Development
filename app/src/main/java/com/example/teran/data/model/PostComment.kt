package com.example.teran.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostComment(
    var commentId: String,
    var userId: String,
    var username: String,
    var comment: String,
    var profilePicture: String,
    var date: String
) : Parcelable
