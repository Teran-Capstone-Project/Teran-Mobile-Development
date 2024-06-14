package com.example.teran.ui.survey.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.teran.R
import com.example.teran.databinding.FragmentDropdownMajorBinding
import com.example.teran.databinding.FragmentDropdownQuestionBinding

class DropdownQuestionFragment : Fragment() {

    private var questionText: String? = null
    private var options: List<String>? = null
    private lateinit var binding: FragmentDropdownQuestionBinding

    companion object {
        private const val ARG_QUESTION_TEXT = "question_text"
        private const val ARG_OPTIONS = "options"

        fun newInstance(questionText: String, options: List<String>): DropdownQuestionFragment {
            val fragment = DropdownQuestionFragment()
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
        binding = FragmentDropdownQuestionBinding.inflate(inflater, container, false)
        binding.questionText.text = questionText
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.list_major_item,
            options ?: mutableListOf()
        )
        binding.gradeOption.setAdapter(adapter)
        return binding.root
    }
}
