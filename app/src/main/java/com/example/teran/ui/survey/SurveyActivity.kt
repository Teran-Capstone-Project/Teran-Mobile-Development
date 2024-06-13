package com.example.teran.ui.survey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.teran.R
import com.example.teran.data.ml.SurveyML
import com.example.teran.databinding.ActivitySurveyBinding

class SurveyActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySurveyBinding

    private lateinit var surveyML: SurveyML
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySurveyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            elevation = 0.0f
            title = "Survey"

            setDisplayHomeAsUpEnabled(true)
        }

        surveyML = SurveyML(
            context = this,
            onResult = { result ->
                println("onResult: " + result)
            },
            onError = { errorMessage ->
                println("onError: " + errorMessage)
            }
        )

        binding.predictBtn.setOnClickListener {
            surveyML.predict(
                jenisKelamin = 0,
                umur = 21,
                jurusan = 1,
                tahunStudi = 4,
                ipk = 4,
                sudahMenikah = 0,
                sedangDepresi = 1,
                sedangCemas = 1,
                sedangPanik = 0,
                sedangMenjalaniPerawatan = 1
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        surveyML.close()
    }
}