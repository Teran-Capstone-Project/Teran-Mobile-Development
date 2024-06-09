package com.example.teran.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.teran.data.database.JournalDao
import com.example.teran.data.database.JournalDatabase
import com.example.teran.data.model.Journal
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class JournalRepository(private val application: Application) {

    private val journalDao: JournalDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = JournalDatabase.getDatabase(application)
        journalDao = db.getJournalDao()
    }
    
    fun insert(journal: Journal) {
        executorService.execute { journalDao.insert(journal) }
    }
    fun update(journal: Journal) {
        executorService.execute { journalDao.update(journal) }
    }

    fun delete(journal: Journal) {
        executorService.execute { journalDao.delete(journal) }
    }

    fun getAll(): LiveData<List<Journal>> = journalDao.getAll()
    fun search(query: String?): LiveData<List<Journal>> = journalDao.search(query)

}