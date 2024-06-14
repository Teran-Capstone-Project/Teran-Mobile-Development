package com.example.teran.ui.survey.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.example.teran.R
import com.example.teran.databinding.FragmentRadioGenderBinding

class RadioGenderFragment : Fragment() {
    private var questionText: String? = null
    private var options: List<String>? = null
    private lateinit var binding: FragmentRadioGenderBinding

    companion object {
        private const val ARG_QUESTION_TEXT = "question_text"
        private const val ARG_OPTIONS = "options"

        fun newInstance(questionText: String, options: List<String>): RadioGenderFragment {
            val fragment = RadioGenderFragment()
            val args = Bundle()
            args.putString(ARG_QUESTION_TEXT, questionText)
            args.putStringArrayList(ARG_OPTIONS, ArrayList(options))
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            questionText = it.getString(ARG_QUESTION_TEXT)
            options = it.getStringArrayList(ARG_OPTIONS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRadioGenderBinding.inflate(inflater, container, false)
        binding.questionText.text = questionText
        options?.forEach { option ->
            val radioButton = RadioButton(requireContext()).apply {
                text = option
                textSize = 16f
            }
            binding.radioGroup.addView(radioButton)
        }
        return binding.root
    }
}
