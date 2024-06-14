package com.example.teran.ui.survey.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.teran.R
import com.example.teran.databinding.FragmentDropdownMajorBinding

class DropdownMajorFragment : Fragment() {
    private var questionText: String? = null
    private var options: List<String>? = null
    private lateinit var binding: FragmentDropdownMajorBinding

    companion object {
        private const val ARG_QUESTION_TEXT = "question_text"
        private const val ARG_OPTIONS = "options"

        fun newInstance(questionText: String, options: List<String>): DropdownMajorFragment {
            val fragment = DropdownMajorFragment()
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
        binding = FragmentDropdownMajorBinding.inflate(inflater, container, false)
        binding.questionText.text = questionText
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.list_major_item,
            options ?: mutableListOf()
        )
        binding.majorOption.setAdapter(adapter)
        return binding.root
    }
}
