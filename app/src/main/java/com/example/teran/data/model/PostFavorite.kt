package com.example.teran.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "post_favorite")
@Parcelize
data class PostFavorite (
    @PrimaryKey
    val postID: String = "",

    @ColumnInfo(name = "desc")
    val desc: String?,

    @ColumnInfo(name = "username")
    val username: String?,

    @ColumnInfo(name = "profilePictureURL")
    val profilePictureURL: String,

    @ColumnInfo(name = "isAnonim")
    val isAnonim: Boolean?,

    @ColumnInfo(name = "userID")
    val userID: String?,

    @ColumnInfo(name = "commentsSize")
    val commentsSize: Int?,

    @ColumnInfo(name = "date")
    val date: String?
) : Parcelable