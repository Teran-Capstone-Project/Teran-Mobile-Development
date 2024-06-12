package com.example.teran.ui.home_page.profile

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.teran.R
import com.example.teran.databinding.FragmentProfileBinding
import com.google.android.material.textfield.TextInputEditText

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


        binding.nameCard.setOnClickListener {
            val editTextLayout = LayoutInflater.from(requireActivity()).inflate(R.layout.layout_edit_text, null)
            val editText = editTextLayout.findViewById<TextInputEditText>(R.id.editText)
            editText.setText(getString(R.string.arry))
            editText.hint = "Nama Kamu"

            val dialog = AlertDialog.Builder(requireActivity())
                .setTitle("Nama Kamu")
                .setView(editTextLayout)
                .setPositiveButton(R.string.simpan, DialogInterface.OnClickListener { dialog, which ->
                    showToast(editText.text.toString())
                })
                .setNegativeButton(R.string.batal, DialogInterface.OnClickListener { dialog, which ->
                })

            dialog.show()
        }

        binding.genderCard.setOnClickListener {
            // Memberikan nilai default index 0
            var indexGenderSelected = 0
            // Menyiapkan daftar data jenis kelamin
            val listGender = arrayOf("Laki-Laki", "Perempuan")
            // Memilih gender sesuai index atau nilai jenis kelamin
            var genderSelected = listGender[indexGenderSelected]

            val dialog = AlertDialog.Builder(requireActivity())
                .setTitle("Pilih Jenis Kelamin")
                .setSingleChoiceItems(listGender, 0) { dialog, which ->
                    // Mengubah nilai index sesuai dengan pilihan pengguna
                    indexGenderSelected = which
                    // Mengubah nilai genderSelected dengan pilihan pengguna
                    genderSelected = listGender[indexGenderSelected]
                }
                .setPositiveButton(R.string.simpan, DialogInterface.OnClickListener { dialog, which ->
                    showToast(genderSelected)
                })
                .setNegativeButton(R.string.batal, DialogInterface.OnClickListener { dialog, which ->
                })

            dialog.show()
        }

        return root
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
}
