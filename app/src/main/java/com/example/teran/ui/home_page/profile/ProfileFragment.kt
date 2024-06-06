package com.example.teran.ui.home_page.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.teran.R
import com.example.teran.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        editUsername()
        editEmail()

        return root
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun editUsername() {
        val username = binding.textUsernameProfile
        val usernameEdt = binding.edtUsernameProfile
        val usernameTV = binding.tvUsernameProfile
        val usernameEditIcon = binding.editUsernameIcon
        val usernameIcon = binding.usernameIcon
        val usernameTitle = binding.usernameTitle

        usernameEditIcon.setOnClickListener {
            if (usernameEdt.visibility == View.GONE) {
                usernameEdt.visibility = View.VISIBLE
                usernameTV.visibility = View.GONE
                usernameIcon.visibility = View.GONE
                usernameTitle.visibility = View.GONE
                usernameEditIcon.setImageResource(R.drawable.baseline_check_24)
            } else {
                val newText = usernameEdt.text.toString()
                usernameTV.text = newText
                username.text = newText
                usernameEdt.visibility = View.GONE
                usernameTV.visibility = View.VISIBLE
                usernameIcon.visibility = View.VISIBLE
                usernameTitle.visibility = View.VISIBLE
                usernameEditIcon.setImageResource(R.drawable.baseline_edit_gray)
            }
        }
    }

    private fun editEmail() {
        val emailTitle = binding.emailTitle
        val emailIcon = binding.emailIcon
        val emailEdt = binding.edtEmailProfile
        val emailTV = binding.tvEmailProfile
        val emailEditIcon = binding.editEmailIcon

        emailEditIcon.setOnClickListener {
            if (emailEdt.visibility == View.GONE) {
                emailEdt.visibility = View.VISIBLE
                emailTV.visibility = View.GONE
                emailIcon.visibility = View.GONE
                emailTitle.visibility = View.GONE
                emailEditIcon.setImageResource(R.drawable.baseline_check_24)
            } else {
                val newText = emailEdt.text.toString()
                emailTV.text = newText
                emailEdt.visibility = View.GONE
                emailTV.visibility = View.VISIBLE
                emailIcon.visibility = View.VISIBLE
                emailTitle.visibility = View.VISIBLE
                emailEditIcon.setImageResource(R.drawable.baseline_edit_gray)
            }
        }
    }
}
