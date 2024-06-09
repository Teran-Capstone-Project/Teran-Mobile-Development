package com.example.teran.ui.journal

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.teran.data.model.Journal
import com.example.teran.data.repository.JournalRepository

class JournalViewModel(application: Application) : ViewModel() {

    private val journalRepository: JournalRepository = JournalRepository(application)

    fun insert(journal: Journal) {
        journalRepository.insert(journal)
    }
    fun update(journal: Journal) {
        journalRepository.update(journal)
    }

    fun delete(journal: Journal) {
        journalRepository.delete(journal)
    }

    fun getAll(): LiveData<List<Journal>> =
        journalRepository.getAll()
    fun search(query: String): LiveData<List<Journal>> =
        journalRepository.search(query)
}