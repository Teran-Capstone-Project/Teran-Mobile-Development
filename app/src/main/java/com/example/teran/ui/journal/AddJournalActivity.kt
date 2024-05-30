package com.example.teran.ui.journal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.teran.R
import com.example.teran.data.database.Note
import com.example.teran.data.helper.DateHelper
import com.example.teran.databinding.ActivityAddJournalBinding
import com.example.teran.viewModelFactory.JournalViewModelFactory

class AddJournalActivity : AppCompatActivity() {

    private var isEdit = false
    private var note: Note? = null
    private lateinit var binding: ActivityAddJournalBinding

    private val viewModel by viewModels<AddJournalViewModel> {
        JournalViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddJournalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        note = intent.getParcelableExtra(EXTRA_NOTE)
        if (note != null) {
            isEdit = true
        } else {
            note = Note()
        }

        finishNote()
        deleteNote()
    }

    private fun deleteNote() {
        binding.addJournalDeleteButton.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.delete_title))
            .setMessage(getString(R.string.message_delete))
            .setPositiveButton(getString(R.string.message_positive)) {dialog, which ->
                note.let {
                    viewModel.delete(note as Note)
                    showToast(getString(R.string.deleted))
                }
                finish()
            }
            .setNegativeButton(getString(R.string.message_negative)) { dialog, which ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun finishNote() {
        binding.finishButton.setOnClickListener {
            val title = binding.addJournalTitleEdt.text.toString().trim()
            val description = binding.addJournalContentEdt.text.toString().trim()
            when {
                title.isEmpty() -> {
                    binding.addJournalTitleEdt.error = getString(R.string.empty)
                }
                description.isEmpty() -> {
                    binding.addJournalContentEdt.error = getString(R.string.empty)
                }
                else -> {
                    note.let { note ->
                        note?.title = title
                        note?.description = description
                    }
                    if (isEdit) {
                        viewModel.update(note as Note)
                        showToast(getString(R.string.changed))
                    } else  {
                        note.let { note ->
                            note?.date = DateHelper.getCurrentDate()
                        }
                        viewModel.insert(note as Note)
                        showToast(getString(R.string.added))
                    }
                    finish()
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    companion object {
        const val EXTRA_NOTE = "extra_note"
    }
}