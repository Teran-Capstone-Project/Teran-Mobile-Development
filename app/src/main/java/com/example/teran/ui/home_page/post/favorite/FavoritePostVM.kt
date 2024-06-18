package com.example.teran.ui.home_page.post.favorite

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teran.data.repository.FavoriteRepository
import com.example.teran.data.sharedpref.MySharedPreferences

class FavoritePostVM (private val favoriteRepository: FavoriteRepository) : ViewModel() {

    fun getFavorite() = favoriteRepository.getAll()

}

