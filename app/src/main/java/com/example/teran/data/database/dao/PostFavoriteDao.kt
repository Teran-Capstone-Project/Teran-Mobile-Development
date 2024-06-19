package com.example.teran.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.teran.data.model.Journal
import com.example.teran.data.model.PostFavorite

@Dao
interface PostFavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(postFavorite: PostFavorite)

    @Delete
    fun delete(postFavorite: PostFavorite)

    @Query("SELECT * FROM post_favorite ORDER BY postID DESC")
    fun getAllPostsFavorites(): LiveData<List<PostFavorite>>

    @Query("SELECT * FROM post_favorite WHERE postID = :postID")
    fun getPostFavoriteByPostID(postID: String): LiveData<PostFavorite>
}