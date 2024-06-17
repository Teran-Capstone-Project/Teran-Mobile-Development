package com.example.teran.ui.survey

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.teran.R
import com.example.teran.data.ml.SurveyML
import com.example.teran.databinding.ActivitySurveyQuestionBinding

class SurveyQuestionActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySurveyQuestionBinding

    private lateinit var surveyML: SurveyML

    private var score: Int? = null
    private var isStress: Boolean? = null

    private var indexQuestion = 1
    private val listOfAnswers = MutableList<Int?>(10) { null }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySurveyQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        supportActionBar?.apply {
            elevation = 0.0f
            title = "Jawab Survey"
        }

        setDropdownQuestion()

        surveyML = SurveyML(
            context = this,
            onResult = { result -> score = (result.toDouble() * 100).toInt() },
            onError = { errorMessage -> showToast("Error: $errorMessage") }
        )

        binding.prevSurveyQuestionBtn.setOnClickListener {
            if (indexQuestion > 1) {
                indexQuestion--
                changeQuestion()
            }
        }
        binding.nextSurveyQuestionBtn.setOnClickListener {
            if (indexQuestion < 10) {
                indexQuestion++
                changeQuestion()
            }
        }

        val radioGroupQuestion = listOf(
            binding.jenisKelaminRG,
            binding.sudahMenikahRG,
            binding.sedangDepresiRG,
            binding.sedangCemasRG,
            binding.sedangPanikRG,
            binding.sedangMenjalaniPerawatanRG
        )
        radioGroupQuestion.forEach {
            it.setOnCheckedChangeListener { group, checkedId ->
                val selectedRadioButton = findViewById<RadioButton>(checkedId)
                listOfAnswers[indexQuestion - 1] = group.indexOfChild(selectedRadioButton)
                saveAnswers()
            }
        }

        val dropDownQuestion = listOf(
            binding.tahunStudiDropdown,
            binding.ipkDropdown
        )
        dropDownQuestion.forEach {
            it.setOnItemClickListener { _, _, position, _ ->
                listOfAnswers[indexQuestion - 1] = position
                saveAnswers()
            }
        }

        // Pertanyaan 2
        binding.umurDropdown.setOnItemClickListener { _, _, position, _ ->
            val selectedValue = binding.umurDropdown.adapter.getItem(position).toString()
            listOfAnswers[indexQuestion - 1] = selectedValue.toInt()
            saveAnswers()
        }

        // Pertanyaan 3
        binding.jurusanDropdown.setOnItemClickListener { _, _, position, _ ->
            val jurusanArray = arrayOf(
                "Teknik Mesin", "Pendidikan Agama Islam", "Hukum", "Matematika",
                "Teknik Elektro", "Ilmu Komputer", "Biologi", "Teknik Perkapalan",
                "Gizi Klinik", "Akuntansi", "Kedokteran", "Fisika",
                "Ilmu Kelautan", "Manajemen", "Perbankan", "Administrasi Bisnis",
                "Olahraga", "Teknik Sipil", "Mesin", "Ilmu biomedis", "Pertanian",
                "Kehutanan", "Keperawatan", "Statistika", "Kebidanan", "Astronomi",
                "Ilmu Pengetahuan Manusia", "Bioteknologi", "Komunikasi",
                "Ilmu Perpustakaan", "Pendidikan Islam", "Aktuaria", "Bahasa Arab",
                "Bahasa Mandarin", "Bahasa Inggris", "Teknik Geodesi",
                "Teknik Industri", "Teknik Geologi", "Ilmu Gizi",
                "Teknik Metalurgi", "ekonomi", "Teknik Fisika", "Kimia",
                "Teknik Kelautan", "Administrasi Perkantoran", "Farmasi",
                "Teknik Kimia", "Ekonomi", "Arsitektur", "Bisnis Digital"
            )

            val jurusanMap = jurusanArray
                .mapIndexed { index, s -> s to index }
                .sortedBy { it.first.toLowerCase() }
                .toMap()

            val adapter = binding.jurusanDropdown.adapter
            listOfAnswers[2] = jurusanMap[adapter.getItem(position).toString()]
            saveAnswers()
        }
    }

    private fun saveAnswers() {
        if (listOfAnswers.all { it != null }) {
            surveyML.predict(
                listOfAnswers[0]!!,
                listOfAnswers[1]!!,
                listOfAnswers[2]!!,
                listOfAnswers[3]!!,
                listOfAnswers[4]!!,
                listOfAnswers[5]!!,
                listOfAnswers[6]!!,
                listOfAnswers[7]!!,
                listOfAnswers[8]!!,
                listOfAnswers[9]!!
            )

            if (score != null) {
                when (score) {
                    in 80..100 -> isStress = true
                    else -> isStress = false
                }

                val intent = Intent(this, SurveyResultActivity::class.java)

                intent.putExtra(SurveyResultActivity.EXTRA_SCORE_RESULT, score.toString())
                intent.putExtra(SurveyResultActivity.EXTRA_IS_STRESS, isStress)

                startActivity(intent)
            }
        }
    }

    private fun changeQuestion() {
        val questionViews = listOf(
            binding.questionSurvey1,
            binding.questionSurvey2,
            binding.questionSurvey3,
            binding.questionSurvey4,
            binding.questionSurvey5,
            binding.questionSurvey6,
            binding.questionSurvey7,
            binding.questionSurvey8,
            binding.questionSurvey9,
            binding.questionSurvey10
        )

        questionViews.forEach { it.visibility = View.GONE }
        questionViews[indexQuestion - 1].visibility = View.VISIBLE
        binding.numberOfQustion.text = "$indexQuestion/10"
    }

    private fun setDropdownQuestion() {
        val umurArray = resources.getStringArray(R.array.umur_array)
        val umurAdapter = ArrayAdapter(this, R.layout.list_item, umurArray)
        binding.umurDropdown.setAdapter(umurAdapter)

        val jurusanArray = resources.getStringArray(R.array.jurusan_array)
        val jurusanAdapter = ArrayAdapter(this, R.layout.list_item, jurusanArray)
        binding.jurusanDropdown.setAdapter(jurusanAdapter)

        val tahunStudiArray = resources.getStringArray(R.array.tahun_studi_array)
        val tahunStudiAdapter = ArrayAdapter(this, R.layout.list_item, tahunStudiArray)
        binding.tahunStudiDropdown.setAdapter(tahunStudiAdapter)

        val ipkArray = resources.getStringArray(R.array.ipk_array)
        val ipkAdapter = ArrayAdapter(this, R.layout.list_item, ipkArray)
        binding.ipkDropdown.setAdapter(ipkAdapter)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
