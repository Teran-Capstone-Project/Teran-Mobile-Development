package com.example.teran.ui.survey

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.teran.R
import com.example.teran.databinding.ActivitySurveyResultBinding
import com.example.teran.ui.home_page.HomePageActivity

class SurveyResultActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SCORE_RESULT = "extra_score_result"
        const val EXTRA_IS_STRESS = "extra_is_stress"
    }

    private lateinit var binding: ActivitySurveyResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySurveyResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            elevation = 0f
            title = "Hasil Survey"
        }

        val scoreResult = intent.getStringExtra(EXTRA_SCORE_RESULT)
        val isStress = intent.getBooleanExtra(EXTRA_IS_STRESS, false)

        when (isStress) {
            true -> {
                binding.stressResultImage.setImageResource(R.drawable.stress_illustration)
                binding.stressResultTitle.text = "Kamu mengalami stres dengan probabilitas $scoreResult%."
                binding.stressRsultDesc.text = "Bestie, stress itu kayak monster yang suka nongol tiba-tiba, bikin kita pusing tujuh keliling. Tapi tenang, ada cara buat ngalahin monster itu! Pertama, coba deh luangin waktu buat me time, dengerin playlist favorit, atau nonton drakor yang lagi hype. Jangan lupa juga buat curhat sama bestie atau orang terdekat, biar beban di hati nggak numpuk. Olahraga juga bisa banget bantu kita ngeluarin hormon bahagia, lho! Kalo stress udah parah banget, jangan ragu buat cari bantuan profesional, ya. Inget, kesehatan mental itu penting banget, bestie! \uD83E\uDEF6✨"
            } false -> {
                binding.stressResultImage.setImageResource(R.drawable.no_stress_illustration)
                binding.stressResultTitle.text = "Kamu tidak mengalami stres dengan probabilitas $scoreResult%."
                binding.stressRsultDesc.text = "Penting banget untuk menjaga kesehatan mental, ya! Kalau kamu ingin tetap chill dan enggak stress, coba deh cari waktu buat istirahat yang cukup. Ngelakuin hobimu juga bisa bantu banget, lho! Terus, jangan lupa jaga komunikasi yang baik sama teman-teman atau keluarga. Kadang ngobrol sama orang terdekat bisa bikin mood jadi lebih baik. Plus, olahraga atau meditasi bisa bikin pikiran jadi lebih fresh. Ingat, yang paling penting itu dengerin tubuh dan perasaanmu sendiri, ya!"
            }
        }

        binding.surveyResultFinishBtn.setOnClickListener {
            val intent = Intent(this, HomePageActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}