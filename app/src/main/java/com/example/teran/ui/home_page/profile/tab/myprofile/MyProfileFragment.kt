package com.example.teran.ui.home_page.profile.tab.myprofile

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.teran.R
import com.example.teran.data.api.ApiConfig
import com.example.teran.data.api.request.profile.UpdateProfileRequest
import com.example.teran.data.api.response.profile.UpdateProfileResponse
import com.example.teran.data.sharedpref.MySharedPreferences
import com.example.teran.databinding.FragmentMyProfileBinding
import com.example.teran.ui.login.LoginActivity
import com.example.teran.ui.main.MainActivity
import com.example.teran.ui.register.RegisterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyProfileFragment : Fragment() {

    private var _binding: FragmentMyProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPref: MySharedPreferences

    private fun setData() {
        sharedPref = MySharedPreferences(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMyProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setData()

        setPicture()
        setUsernameAndEmail()
        setLogoutBtn()
        setNameCard()

        return root
    }

    private fun setNameCard() {
        binding.nameCard.setOnClickListener {
            val editTextLayout = LayoutInflater.from(requireActivity()).inflate(R.layout.layout_edit_text, null)
            val editText = editTextLayout.findViewById<EditText>(R.id.editText)
            editText.setText(sharedPref.getUser().name)
            editText.hint = "Nama Kamu"

            val dialog = AlertDialog.Builder(requireActivity())
                .setTitle("Nama Kamu")
                .setView(editTextLayout)
                .setPositiveButton(R.string.simpan, DialogInterface.OnClickListener { dialog, which ->
                    updateUserName(editText.text.toString())
                })
                .setNegativeButton(R.string.batal, DialogInterface.OnClickListener { dialog, which ->
                })

            dialog.show()
        }
    }

    private fun updateUserName(newName: String) {
        ApiConfig.getApiService().updateProfile(
            "Bearer ${sharedPref.getUser().token}",
            UpdateProfileRequest(newName)
        ).enqueue(object : Callback<UpdateProfileResponse> {
            override fun onResponse(
                call: Call<UpdateProfileResponse>,
                response: Response<UpdateProfileResponse>
            ) {
                println(response)
                val responseBody = response.body()
                if (responseBody != null) {
                    // update Local Data
                    sharedPref.updateName(newName)
                    // update UI
                    binding.nameValue.text = newName
                    showToast("Berhasil Memperbarui Nama")
                }
            }

            override fun onFailure(call: Call<UpdateProfileResponse>, t: Throwable) {
                println(t.message)
            }

        })
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

    private fun setUsernameAndEmail() {
        binding.nameValue.text = sharedPref.getUser().name
        binding.emailValue.text = sharedPref.getUser().email
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

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

}