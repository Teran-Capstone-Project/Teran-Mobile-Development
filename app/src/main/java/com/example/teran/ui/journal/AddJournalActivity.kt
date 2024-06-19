package com.example.teran.ui.journal

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.teran.R
import com.example.teran.data.helper.DateHelper
import com.example.teran.data.model.Journal
import com.example.teran.data.viewmodel.ViewModelFactory
import com.example.teran.databinding.ActivityAddJournalBinding
import com.google.android.material.chip.Chip

class AddJournalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddJournalBinding

    private lateinit var journalViewModel: JournalViewModel

    private var moodSelected: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddJournalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            elevation = 0.0f
            title = "Buat Journal"

            setDisplayHomeAsUpEnabled(true)
        }

        journalViewModel = obtainViewModel(this@AddJournalActivity)

        // Salah satu mood chip dipilih
        binding.moodChipGroup.setOnCheckedChangeListener { group, checkedId ->
            // Apakah ada chip group yang dipilih
            val isSelected = group.checkedChipId != -1

            // Jika ada chip group yang dipilih
            if (isSelected) {
                // Mengambil view chip
                val chipItem: Chip = group.findViewById(checkedId)
                // Memberikan value text chip yang dipilih
                moodSelected = chipItem.text.toString()
            }
            // Jika tidak ada grup chip yang dipilih
            else {
                moodSelected = null
            }
        }

        binding.addJournalBtn.setOnClickListener {
            val desc = binding.descJournalEdtText.text.toString().trim()

            when {
                desc.isEmpty() -> {
                    binding.descJournalEdtLayout.error = getString(R.string.message_isi_jurnal)
                }
                else -> {
                    journalViewModel.insert(
                        Journal(
                            null,
                            moodSelected,
                            desc,
                            DateHelper.getCurrentDate()
                        )
                    )
                    finish()
                    showToast("Berhasil Menambahkan Jurnal")
                }
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): JournalViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[JournalViewModel::class.java]
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}