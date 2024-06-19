package com.example.teran.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.teran.data.database.TeranDatabase
import com.example.teran.data.database.dao.PostFavoriteDao
import com.example.teran.data.model.PostFavorite
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class PostFavoriteRepository(private val application: Application) {

    private val postFavoriteDao: PostFavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = TeranDatabase.getDatabase(application)
        postFavoriteDao = db.getPostFavoriteDao()
    }

    fun getAllPostsFavorites(): LiveData<List<PostFavorite>> = postFavoriteDao.getAllPostsFavorites()

    fun insert(postFavorite: PostFavorite) {
        executorService.execute { postFavoriteDao.insert(postFavorite) }
    }

    fun delete(postFavorite: PostFavorite) {
        executorService.execute { postFavoriteDao.delete(postFavorite) }
    }

    fun search(query: String): LiveData<PostFavorite> {
        return postFavoriteDao.getPostFavoriteByPostID(query)
    }

}