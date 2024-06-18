package com.example.teran.ui.home_page.profile

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.teran.R
import com.example.teran.data.sharedpref.MySharedPreferences
import com.example.teran.databinding.FragmentProfileBinding
import com.example.teran.ui.main.MainActivity
import com.google.android.material.textfield.TextInputEditText
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.request.ImageResult
import com.example.teran.ui.login.LoginActivity
import com.example.teran.ui.register.RegisterActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPref: MySharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        sharedPref = MySharedPreferences(requireActivity())

        if (sharedPref.getUser().token != null) {
            binding.displayAuth.visibility = View.VISIBLE

            setPicture()
            setUsername()
            setLogoutBtn()
        } else {
            binding.displayGuest.visibility = View.VISIBLE

            setLoginBtn()
            setRegisterBtn()
        }

        return root
    }

    private fun setPicture() {
        val imageLoader = ImageLoader.Builder(requireActivity())
            .components {
                add(SvgDecoder.Factory())
            }
            .build()

        val imageRequest = ImageRequest.Builder(requireActivity())
            .data(sharedPref.getUser().profilePicture)
            .target(binding.imageProfile)
            .build()

        imageLoader.enqueue(imageRequest)
    }

    private fun setUsername() {
        binding.nameValue.text = sharedPref.getUser().name
    }

    private fun setLogoutBtn() {
        binding.logoutBtn.setOnClickListener {
            sharedPref.clear()

            val intent = Intent(requireActivity(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

            showToast("Berhasil Logout")
        }
    }

    private fun setLoginBtn() {
        binding.profileLoginBtn.setOnClickListener {
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setRegisterBtn() {
        binding.profileRegisterBtn.setOnClickListener {
            val intent = Intent(requireActivity(), RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
}
