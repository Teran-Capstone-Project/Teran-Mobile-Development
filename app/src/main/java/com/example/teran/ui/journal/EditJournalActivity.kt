package com.example.teran.ui.journal

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.teran.R
import com.example.teran.data.model.Journal
import com.example.teran.data.viewmodel.ViewModelFactory
import com.example.teran.databinding.ActivityEditJournalBinding
import com.google.android.material.chip.Chip

class EditJournalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditJournalBinding

    private lateinit var journalViewModel: JournalViewModel

    private var journal: Journal? = null

    private var moodSelected: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditJournalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            elevation = 0.0f
            title = "Edit Journal"

            setDisplayHomeAsUpEnabled(true)
        }

        journal = intent.getParcelableExtra(EXTRA_JOURNAL)

        setEditData()

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

        journalViewModel =  obtainViewModel(this@EditJournalActivity)
        binding.editJournalBtn.setOnClickListener {
            val desc = binding.descJournalEdtText.text.toString().trim()

            if (desc.isEmpty()) {
                binding.descJournalEdtLayout.error = getString(R.string.message_isi_jurnal)
            } else {
                journalViewModel.update(
                    Journal(
                        journal?.id,
                        moodSelected,
                        desc,
                        journal!!.date
                    )
                )
                showToast(getString(R.string.message_memperbarui_jurnal))
                finish()
            }
        }
    }

    private fun setEditData() {
        journal.let {
            // Mengambil data mood
            when(it?.mood) {
                getString(R.string.bahagia) -> {
                    binding.moodBahagiaChip.isChecked = true
                    moodSelected = getString(R.string.bahagia)
                }
                getString(R.string.tenang) -> {
                    binding.moodTenangChip.isChecked = true
                    moodSelected = getString(R.string.tenang)
                }
                getString(R.string.netral) -> {
                    binding.moodNetralChip.isChecked = true
                    moodSelected = getString(R.string.netral)
                }
                getString(R.string.cemas) -> {
                    binding.moodCemasChip.isChecked = true
                    moodSelected = getString(R.string.cemas)
                }
                getString(R.string.sedih) -> {
                    binding.moodSedihChip.isChecked = true
                    moodSelected = getString(R.string.sedih)
                }
            }

            // Mengambil data deskripsi
            binding.descJournalEdtText.setText(it?.desc)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit_journal, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.deleteJournal -> {
                journalViewModel.delete(
                    Journal(
                        journal?.id,
                        "",
                        "",
                        ""
                    )
                )
                showToast(getString(R.string.message_hapus_jurnal))
                finish()
            }
        }

        return true
    }

    private fun obtainViewModel(activity: AppCompatActivity): JournalViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[JournalViewModel::class.java]
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_JOURNAL = "extra_journal"
    }
}