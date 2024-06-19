package com.example.teran.data.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.teran.ui.home_page.post.PostViewModel
import com.example.teran.ui.home_page.post.detail.DetailPostVM
import com.example.teran.ui.home_page.post.favorite.PostFavoriteVM
import com.example.teran.ui.home_page.profile.tab.mypost.MyPostViewModel
import com.example.teran.ui.journal.JournalViewModel

class ViewModelFactory private constructor(private val application: Application) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JournalViewModel::class.java)) {
            return JournalViewModel(application) as T
        } else if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            return PostViewModel(application) as T
        } else if (modelClass.isAssignableFrom(DetailPostVM::class.java)) {
            return DetailPostVM(application) as T
        } else if (modelClass.isAssignableFrom(PostFavoriteVM::class.java)) {
            return PostFavoriteVM(application) as T
        } else if (modelClass.isAssignableFrom(MyPostViewModel::class.java)) {
            return MyPostViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}