package com.example.teran.ui.login

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.widget.addTextChangedListener
import com.example.teran.R
import com.example.teran.databinding.ActivityLoginBinding
import com.example.teran.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setPassword()
        moveToRegister()
    }

    private fun moveToRegister() {
        binding.registerLink.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setPassword() {
        binding.passwordEdtText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val password = s.toString()
                val passwordLayout = binding.passwordedtLayout

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
}