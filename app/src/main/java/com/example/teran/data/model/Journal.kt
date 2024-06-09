package com.example.teran.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Entity(tableName = "journal")
@Parcelize
data class Journal(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    @ColumnInfo(name = "mood")
    val mood: String?,

    @ColumnInfo(name = "desc")
    val desc: String,

    @ColumnInfo(name = "date")
    val date: String
) : Parcelable