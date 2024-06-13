package com.example.teran.ui.survey

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.teran.R
import com.example.teran.data.ml.SurveyML
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
        }

        binding.batalSurveyBtn.setOnClickListener {
            finish()
        }
        binding.mulaiSurveyBtn.setOnClickListener {
            val intent = Intent(this@SurveyActivity, SurveyQuestionActivity::class.java)
            startActivity(intent)
        }
    }
}