package com.example.teran.ui.survey.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.teran.databinding.FragmentTextQuestionBinding

class TextQuestionFragment : Fragment() {

    private var questionText: String? = null
    private lateinit var binding: FragmentTextQuestionBinding

    companion object {
        private const val ARG_QUESTION_TEXT = "question_text"

        fun newInstance(questionText: String): TextQuestionFragment {
            val fragment = TextQuestionFragment()
            val args = Bundle()
            args.putString(ARG_QUESTION_TEXT, questionText)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            questionText = it.getString(ARG_QUESTION_TEXT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTextQuestionBinding.inflate(inflater, container, false)
        binding.questionText.text = questionText
        return binding.root
    }
}
