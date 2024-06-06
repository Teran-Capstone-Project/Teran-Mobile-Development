package com.example.teran.ui.home_page.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.teran.databinding.FragmentHomeBinding
import com.example.teran.ui.journal.JournalActivity
import com.example.teran.ui.meditation.MeditationActivity
import com.example.teran.ui.survey.SurveyActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val homeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val cardViewSurvey: CardView = binding.cardView
        val cardViewJournal: CardView = binding.cardViewJournal
        val cardViewMeditation: CardView = binding.cardViewMeditation

        val user: TextView = binding.textUsernameHome
        val quoteOfTheDay: TextView = binding.textQuoteContent
        homeViewModel.text.observe(viewLifecycleOwner) {
            user.text = it
        }

        homeViewModel.quote.observe(viewLifecycleOwner) {
            quoteOfTheDay.text = it
        }

        cardViewSurvey.setOnClickListener {
            val intent = Intent(requireActivity(), SurveyActivity::class.java)
            startActivity(intent)
        }

        cardViewJournal.setOnClickListener {
            val intent = Intent(requireActivity(), JournalActivity::class.java)
            startActivity(intent)
        }

        cardViewMeditation.setOnClickListener {
            val intent = Intent(requireActivity(), MeditationActivity::class.java)
            startActivity(intent)
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.updateQuote()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}