package com.example.teran.ui.journal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teran.R
import com.example.teran.databinding.ActivityJournalBinding
import com.example.teran.databinding.ListItemJournalBinding
import com.example.teran.ui.adapter.JournalAdapter
import com.example.teran.viewModelFactory.JournalViewModelFactory

class JournalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJournalBinding

    private val viewModel by viewModels<JournalViewModel> {
        JournalViewModelFactory.getInstance(application)
    }

    private lateinit var adapter: JournalAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJournalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabJournal.setOnClickListener {
            val intent = Intent(this, AddJournalActivity::class.java)
            startActivity(intent)
        }

        adapter = JournalAdapter()

        binding.rvJournal.layoutManager = LinearLayoutManager(this)
        binding.rvJournal.setHasFixedSize(true)
        binding.rvJournal.adapter = adapter

        viewModel.getAllNotes().observe(this) {noteList ->
            if (noteList != null) {
                adapter.setListNotes(noteList)
            }
        }

    }


}