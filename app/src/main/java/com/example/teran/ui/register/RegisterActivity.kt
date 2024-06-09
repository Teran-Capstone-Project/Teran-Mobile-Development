package com.example.teran.ui.register

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import com.example.teran.R
import com.example.teran.databinding.ActivityRegisterBinding
import com.example.teran.ui.login.LoginActivity
import java.util.Calendar

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupGenderDropdown()
        setupDateOfBirthPicker()
        confirmPassword()
        setupPassword()
        moveToLogin()
    }

    private fun moveToLogin() {
        binding.loginLink.setOnClickListener {
        val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupPassword() {
        binding.createPasswordEdtText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val password = s.toString()
                val passwordLayout = binding.createPasswordedtLayout

                when {
                    password.length < 8 -> { //if password below 8 character
                        passwordLayout.error = getString(R.string.invalid_length_password)
                    }
                    !password.matches(Regex(".*[A-Za-z].*")) -> { //if password has no letter
                        passwordLayout.error = getString(R.string.noLetter_password)
                    }
                    !password.matches(Regex(".*\\d.*")) -> { //if password has no digit
                        passwordLayout.error  = getString(R.string.noDigit_password)
                    }
                    else -> { // Clear the error
                        passwordLayout.error = null
                        passwordLayout.helperText = null
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
                //Do nothing
            }
        })
    }

    private fun confirmPassword() {
        binding.confirmPasswordEdtText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val confirmPassword = s.toString()
                val password = binding.createPasswordEdtText.text.toString()
                val confirmPasswordLayout = binding.confirmPasswordedtLayout

                if (password != confirmPassword) {
                    confirmPasswordLayout.error = getString(R.string.match_password)
                } else {
                    confirmPasswordLayout.error = null
                    confirmPasswordLayout.helperText = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }
        })

    }

    private fun setupDateOfBirthPicker() {
        binding.dobEdtLayout.setEndIconOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                binding.DoBEdtText.setText("$selectedDay/${selectedMonth + 1}/$selectedYear")
            },
            year, month, day
        )

        datePickerDialog.show()
    }

    private fun setupGenderDropdown() {
        val genderOptions = resources.getStringArray(R.array.gender_options)
        val adapter = ArrayAdapter(this, R.layout.list_item_gender, genderOptions)
        binding.autoCompleteText.setAdapter(adapter)
    }
}