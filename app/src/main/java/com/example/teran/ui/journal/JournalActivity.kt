package com.example.teran.ui.journal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.teran.R
import com.example.teran.databinding.ActivityJournalBinding

class JournalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJournalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJournalBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}