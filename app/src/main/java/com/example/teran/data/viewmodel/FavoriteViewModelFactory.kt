package com.example.teran.data.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.teran.data.di.Injection
import com.example.teran.data.repository.FavoriteRepository
import com.example.teran.ui.home_page.post.favorite.FavoritePostVM

class FavoriteViewModelFactory private constructor(private val repository: FavoriteRepository):
    ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritePostVM::class.java)) {
            return FavoritePostVM(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: FavoriteViewModelFactory? = null

        fun getInstance(application: Application): FavoriteViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: FavoriteViewModelFactory(Injection.provideRepository(application.applicationContext))
            }.also { instance= it }
    }
}