package com.example.teran.ui.survey.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.teran.R
import com.example.teran.data.model.QuestionType
import com.example.teran.data.model.Survey
import com.example.teran.ui.survey.fragment.DropdownMajorFragment
import com.example.teran.ui.survey.fragment.DropdownQuestionFragment
import com.example.teran.ui.survey.fragment.RadioGenderFragment
import com.example.teran.ui.survey.fragment.RadioQuestionFragment
import com.example.teran.ui.survey.fragment.TextQuestionFragment
import com.example.teran.ui.survey.fragment.YearQuestionFragment

class SurveyPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val questions: List<Survey>

    init {
        questions = listOf(
            Survey("Jenis kelamin kamu?", QuestionType.GENDER, listOf("Pria", "Wanita")),
            Survey("Umur kamu?", QuestionType.TEXT),
            Survey("Jurusan kamu?", QuestionType.MAJOR, fragmentActivity.resources.getStringArray(R.array.major_options).toList()),
            Survey("Tahun Studi?", QuestionType.YEAR),
            Survey("IPK kamu?", QuestionType.DROPDOWN, fragmentActivity.resources.getStringArray(R.array.grade_options).toList()),
            Survey("Sedang Menikah?", QuestionType.RADIO, listOf("Ya", "Tidak")),
            Survey("Sedang Depresi?", QuestionType.RADIO, listOf("Ya", "Tidak")),
            Survey("Sedang Cemas?", QuestionType.RADIO, listOf("Ya", "Tidak")),
            Survey("Sedang Panik?", QuestionType.RADIO, listOf("Ya", "Tidak")),
            Survey("Sedang Menjalani perawatan?", QuestionType.RADIO, listOf("Ya", "Tidak"))
        )
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    override fun createFragment(position: Int): Fragment {
        val question = questions[position]
        return when (question.type) {
            QuestionType.RADIO -> RadioQuestionFragment.newInstance(question.text, question.options)
            QuestionType.TEXT -> TextQuestionFragment.newInstance(question.text)
            QuestionType.YEAR -> YearQuestionFragment.newInstance(question.text)
            QuestionType.DROPDOWN -> DropdownQuestionFragment.newInstance(question.text, question.options)
            QuestionType.GENDER -> RadioGenderFragment.newInstance(question.text, question.options)
            QuestionType.MAJOR -> DropdownMajorFragment.newInstance(question.text, question.options)
        }
    }
}


