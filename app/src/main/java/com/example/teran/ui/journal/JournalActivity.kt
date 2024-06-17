package com.example.teran.ui.journal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.teran.data.viewmodel.ViewModelFactory
import com.example.teran.databinding.ActivityJournalBinding

class JournalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJournalBinding

    private lateinit var adapter: JournalAdapter

    private lateinit var journalViewModel: JournalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJournalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            elevation = 0.0f
            title = "Journal"

            setDisplayHomeAsUpEnabled(true)
        }

        journalViewModel = obtainViewModel(this@JournalActivity)

        journalViewModel.getAll().observe(this) { listJournals ->
            adapter.setListJournals(listJournals)

            println(listJournals)
            println("==================")

            if (listJournals.isNotEmpty()) {
                binding.journalEmptyImage.visibility = View.GONE
                binding.journalEmptyMessage.visibility = View.GONE
            } else {
                binding.journalEmptyImage.visibility = View.VISIBLE
                binding.journalEmptyMessage.visibility = View.VISIBLE
            }
        }

        setJournalAdapter()

        binding.journalSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // Method dilakukan setiap disubmit
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            // Method dilakukan setiap ketikan
            override fun onQueryTextChange(newText: String?): Boolean {
                // Data text ketikan di search view
                val inputText: String = newText?.trim() ?: ""

                // Jika inputText tidak kosong ...
                if (inputText.isNotEmpty()) {
                    // Lakukan pencarian jurnal
                    journalViewModel.search("%$inputText%").observe(this@JournalActivity) { listJournals ->
                        // Perbarui Journal Adapter
                        println("search: " + listJournals)
                        println("==================")
                        adapter.setListJournals(listJournals)
                    }
                }
                // Jika inputText kosong ...
                else {
                    // Lakukan pengambilan semua jurnal
                    journalViewModel.getAll().observe(this@JournalActivity) { listJournals ->
                        // Perbarui Journal Adapter
                        println("getAll: " + listJournals)
                        println("==================")
                        adapter.setListJournals(listJournals)
                    }
                }

                return true
            }
        })

        // Pengguna menekan tombol tambah jurnal
        binding.fabAddJournal.setOnClickListener {
            val intent = Intent(this@JournalActivity, AddJournalActivity::class.java)
            startActivity(intent)
            recreate()
        }
    }

    override fun onPause() {
        super.onPause()
        binding.journalSearchView.setQuery("", false)
    }

    private fun setJournalAdapter() {
        adapter = JournalAdapter()

        binding.apply {
//            journalRecyclerView.layoutManager = LinearLayoutManager(this@JournalActivity)
            journalRecyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            journalRecyclerView.setHasFixedSize(true)
            journalRecyclerView.adapter = adapter
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): JournalViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[JournalViewModel::class.java]
    }
}