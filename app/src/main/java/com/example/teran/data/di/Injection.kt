package com.example.teran.data.di

import android.content.Context
import com.example.teran.data.api.ApiConfig
import com.example.teran.data.database.favorite.FavoriteDatabase
import com.example.teran.data.repository.FavoriteRepository

object Injection {
    fun provideRepository(context: Context): FavoriteRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteDatabase.getDatabase(context)
        val dao = database.getFavoriteDao()
        return FavoriteRepository.getInstance(apiService, dao)
    }
}