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
import com.example.teran.ui.home_page.profile.tab.ViewPagerProfile
import com.example.teran.ui.login.LoginActivity
import com.example.teran.ui.register.RegisterActivity
import com.google.android.material.tabs.TabLayoutMediator

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

        val viewPager = binding.viewPagerProfile
        val tabLayout = binding.tabLayoutProfile

        val adapter = ViewPagerProfile(childFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Profile"
                1 -> tab.text = "My Posts"
            }
        }.attach()

        return root
    }
}
