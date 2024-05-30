package com.example.teran.viewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.teran.ui.journal.AddJournalViewModel
import com.example.teran.ui.journal.JournalViewModel

class JournalViewModelFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddJournalViewModel::class.java)) {
            return AddJournalViewModel(mApplication) as T
        }
        else if (modelClass.isAssignableFrom(JournalViewModel::class.java)) {
            return JournalViewModel(mApplication) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: JournalViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): JournalViewModelFactory {
            if (INSTANCE == null) {
                synchronized(JournalViewModelFactory::class.java) {
                    INSTANCE = JournalViewModelFactory(application)
                }
            }
            return INSTANCE as JournalViewModelFactory
        }
    }
}