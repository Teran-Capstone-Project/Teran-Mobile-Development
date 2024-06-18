package com.example.teran.data.repository

import androidx.lifecycle.LiveData
import com.example.teran.data.api.ApiService
import com.example.teran.data.database.favorite.FavoriteDao
import com.example.teran.data.model.Favorite

class FavoriteRepository(
    val apiService: ApiService,
    val favoriteDao: FavoriteDao
) {
    fun getAll(): LiveData<List<Favorite>> = favoriteDao.getAll()

    fun insert(favorite: Favorite) {
        favoriteDao.insert(favorite)
    }

    fun delete(favorite: Favorite) {
        favoriteDao.delete(favorite)
    }

    fun removeFavorite(username: String) {
        return favoriteDao.removeFavorite(username)
    }

    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null

        fun getInstance(
            apiService: ApiService,
            favoriteDao: FavoriteDao
        ): FavoriteRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteRepository(apiService, favoriteDao)
            }
    }
}