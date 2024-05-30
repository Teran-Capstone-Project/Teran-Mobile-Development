package com.example.teran.ui.journal

import android.app.Application

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.teran.data.database.Note
import com.example.teran.data.repository.NoteRepository

class JournalViewModel(application: Application) : ViewModel() {
    private val repository: NoteRepository = NoteRepository(application)

    fun getAllNotes(): LiveData<List<Note>> = repository.getAllNotes()

}