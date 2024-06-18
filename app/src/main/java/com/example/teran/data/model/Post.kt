package com.example.teran.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    var id: String,
    var desc: String,
    var username: String,
    var profilePictureURL: String,
    var isAnonim: Boolean,
    var commentsSize: Int,
    var date: String
) : Parcelable