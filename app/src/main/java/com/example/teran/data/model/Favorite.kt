package com.example.teran.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity (tableName = "favorite")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int?,
    @ColumnInfo(name = "desc")
    var desc: String,
    @ColumnInfo(name = "username")
    var username: String,
    @ColumnInfo(name = "profilePictureURL")
    var profilePictureURL: String,
    @ColumnInfo(name = "isAnonim")
    var isAnonim: Boolean,
    @ColumnInfo(name = "commentsSize")
    var commentsSize: Int,
    @ColumnInfo(name = "date")
    var date: String
) : Parcelable
