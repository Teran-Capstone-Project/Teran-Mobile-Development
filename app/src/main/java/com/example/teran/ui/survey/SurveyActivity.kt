package com.example.teran.ui.survey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.teran.R
import com.example.teran.databinding.ActivitySurveyBinding

class SurveyActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySurveyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySurveyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            elevation = 0.0f
            title = "Survey"

            setDisplayHomeAsUpEnabled(true)
        }
    }
}