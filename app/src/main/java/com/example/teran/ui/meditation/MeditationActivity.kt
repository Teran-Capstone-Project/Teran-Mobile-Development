package com.example.teran.ui.meditation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.teran.R
import com.example.teran.databinding.ActivityMeditationBinding

class MeditationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMeditationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeditationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}