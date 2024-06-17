package com.example.teran.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.example.teran.R
import com.example.teran.data.api.ApiConfig
import com.example.teran.data.api.request.RegisterRequest
import com.example.teran.data.api.response.auth.RegisterResponse
import com.example.teran.databinding.ActivityRegisterBinding
import com.example.teran.ui.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupName()
        setupEmail()
        setupPassword()
        confirmPassword()

        binding.loginLink.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            finish()
            startActivity(intent)
        }

        binding.registerButton.setOnClickListener {
            val email = binding.emailEdtText.text.toString()
            val name = binding.nameEdtText.text.toString()
            val createPassword = binding.createPasswordEdtText.text.toString()
            val confirmPassword = binding.confirmPasswordEdtText.text.toString()

            if (name.isEmpty()) {
                binding.nameEdtLayout.error = getString(R.string.enter_name)
            }

            if (email.isEmpty()) {
                binding.emailEdtLayout.error = getString(R.string.enter_email)
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailEdtLayout.error = getString(R.string.invalid_format_email)
            }

            if (createPassword.isEmpty()) {
                binding.createPasswordedtLayout.error = getString(R.string.enter_password)
            }

            if (confirmPassword.isEmpty()) {
                binding.confirmPasswordedtLayout.error = getString(R.string.enter_password)
            }

            if (name.isNotEmpty() &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                createPassword.length >= 8 &&
                confirmPassword == createPassword) {
                showLoading(true)
                register(email, name, confirmPassword)
            }
        }
    }

    private fun register(email: String, name: String, password: String) {
        ApiConfig.getApiService().authRegister(
            RegisterRequest(
                email, name, password
            )
        ).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                showLoading(false)

                if (response.code() == 200) {
                    showToast("Email ini sudah terdaftar. Silakan coba dengan email lain.")
                } else if (response.code() == 201) {
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    finish()
                    startActivity(intent)

                    showToast("Berhasil Membuat Akun")
                    showToast("Silahkan Masuk")
                }
            }
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                showLoading(false)
                showToast(t.message.toString())
            }
        })
    }

    private fun setupName() {
        binding.nameEdtText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val nameText = s.toString()
                val nameLayout = binding.nameEdtLayout

                if (nameText.isEmpty()) {
                    nameLayout.error = getString(R.string.enter_name)
                } else {
                    nameLayout.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }

        })
    }

    private fun setupEmail() {
        binding.emailEdtText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val email = binding.emailEdtText.text.toString()
                val emailLayout = binding.emailEdtLayout

                when {
                    !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> { // if email is invalid
                        emailLayout.error = getString(R.string.invalid_format_email)
                    } else -> {
                        emailLayout.error = null
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
                //Do nothing
            }

        })
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
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }
        })

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            binding.progressBarRegister.visibility = View.VISIBLE
            binding.viewProgressRegister.visibility = View.VISIBLE
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            binding.progressBarRegister.visibility = View.GONE
            binding.viewProgressRegister.visibility = View.GONE
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
    }
}