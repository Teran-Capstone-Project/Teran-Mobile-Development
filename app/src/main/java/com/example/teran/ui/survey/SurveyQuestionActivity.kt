package com.example.teran.ui.survey

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.teran.R
import com.example.teran.data.ml.SurveyML
import com.example.teran.databinding.ActivitySurveyQuestionBinding
import com.example.teran.ui.survey.adapter.SurveyPagerAdapter

class SurveyQuestionActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySurveyQuestionBinding

    private lateinit var surveyML: SurveyML

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySurveyQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager = binding.viewPager
        val adapter = SurveyPagerAdapter(this)
        viewPager.adapter = adapter

        val buttonNext = binding.buttonNext
        val buttonBack = binding.buttonBack

        buttonNext.setOnClickListener {
            val currentItem = viewPager.currentItem
            if (currentItem < adapter.itemCount - 1) {
                viewPager.currentItem = currentItem + 1
            }
        }

        buttonBack.setOnClickListener {
            val currentItem = viewPager.currentItem
            if (currentItem > 0) {
                viewPager.currentItem = currentItem - 1
            }
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val pageNumberText = "${position + 1}/${adapter.itemCount}"
                binding.TVPageNumber.text = pageNumberText
                // Menghilangkan tombol back saat halaman awal
                buttonBack.visibility = if (position == 0) View.INVISIBLE else View.VISIBLE
                // Menghilangkan tombol next saat halaman akhir
                buttonNext.visibility = if (position == adapter.itemCount - 1) View.INVISIBLE else View.VISIBLE
            }
        })

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