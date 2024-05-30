package com.example.teran.ui.journal

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.teran.data.database.Note
import com.example.teran.data.repository.NoteRepository

class AddJournalViewModel(application: Application): ViewModel() {
    private val repository: NoteRepository = NoteRepository(application)

    fun insert(note: Note) {
        repository.insert(note)
    }

    fun update(note: Note) {
        repository.update(note)
    }

    fun delete(note: Note) {
        repository.delete(note)
    }
}