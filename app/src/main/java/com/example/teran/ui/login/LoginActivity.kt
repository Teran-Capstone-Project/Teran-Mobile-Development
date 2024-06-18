package com.example.teran.ui.login

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
import com.example.teran.data.api.request.LoginRequest
import com.example.teran.data.api.response.auth.LoginResponse
import com.example.teran.data.model.User
import com.example.teran.data.sharedpref.MySharedPreferences
import com.example.teran.databinding.ActivityLoginBinding
import com.example.teran.ui.home_page.HomePageActivity
import com.example.teran.ui.register.RegisterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var sharedPref: MySharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        sharedPref = MySharedPreferences(this)

        setupEmail()
        setupPassword()

        binding.emailEdtText.setText("tester@gmail.com")
        binding.passwordEdtText.setText("password123")

        binding.registerLink.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailEdtText.text.toString()
            val pass = binding.passwordEdtText.text.toString()

            if (email.isEmpty()) {
                binding.emailEdtLayout.error = getString(R.string.enter_email)
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailEdtLayout.error = getString(R.string.invalid_format_email)
            }

            if (pass.isEmpty()) {
                binding.passwordEdtLayout.error = getString(R.string.enter_password)
            }

            if (Patterns.EMAIL_ADDRESS.matcher(email).matches() && pass.isNotEmpty()) {
                showLoading(true)
                login(email, pass)
            }
        }
    }

    private fun login(email: String, pass: String) {
        ApiConfig.getApiService().authLogin(
            LoginRequest(email, pass)
        ).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                showLoading(false)
                println(response)
                if (response.code() == 404) {
                    showToast("Akun Belum Terdaftar")
                    showToast("Silahkan Registrasi untuk Membuat Akun")
                } else if (response.code() == 200) {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        sharedPref.addUser(
                            User(
                                responseBody.token,
                                responseBody.user.id,
                                responseBody.user.name,
                                responseBody.user.email,
                                responseBody.user.password,
                                responseBody.user.profilePicture,
                                responseBody.user.role,
                            )
                        )

                        val intent = Intent(this@LoginActivity, HomePageActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                        showToast("Selamat datang ${responseBody.user.name}")
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                showToast("Gagal Login")
                println(t.message)
                showLoading(false)
            }
        })
    }

    private fun setupEmail() {
        binding.emailEdtText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val emailText = s.toString()
                val emailLayout = binding.emailEdtLayout

                if (emailText.isEmpty()) {
                    emailLayout.error = getString(R.string.enter_email)
                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                    emailLayout.error = getString(R.string.invalid_format_email)
                } else {
                    emailLayout.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }
        })
    }

    private fun setupPassword() {
        binding.passwordEdtText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val passText = s.toString()
                val passLayout = binding.passwordEdtLayout

                if (passText.isEmpty()) {
                    passLayout.error = getString(R.string.enter_password)
                } else {
                    binding.passwordEdtLayout.error = null
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
            binding.progressBarLogin.visibility = View.VISIBLE
            binding.progressViewLogin.visibility = View.VISIBLE
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            binding.progressBarLogin.visibility = View.GONE
            binding.progressViewLogin.visibility = View.GONE
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
    }
}