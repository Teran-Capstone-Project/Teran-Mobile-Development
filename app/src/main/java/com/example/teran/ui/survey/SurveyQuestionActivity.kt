package com.example.teran.ui.survey

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.teran.R
import com.example.teran.data.ml.SurveyML
import com.example.teran.databinding.ActivitySurveyQuestionBinding

class SurveyQuestionActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySurveyQuestionBinding

    private lateinit var surveyML: SurveyML

    private val listQuestion = listOf<String>(
        "Jenis kelamin kamu",
        "Umur kamu",
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySurveyQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            elevation = 0.0f
            title = "Jawab Survey"
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