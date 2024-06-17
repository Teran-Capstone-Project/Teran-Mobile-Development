package com.example.teran.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.teran.R
import com.example.teran.data.sharedpref.MySharedPreferences
import com.example.teran.databinding.ActivityMainBinding
import com.example.teran.ui.home_page.HomePageActivity
import com.example.teran.ui.login.LoginActivity
import com.example.teran.ui.register.RegisterActivity
import com.example.teran.data.helper.IsDarkThemeOn

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var sharedPref: MySharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        if (IsDarkThemeOn.isDarkThemeOn(this)) {
            binding.teranLogo.setImageResource(R.drawable.logo_dark)
        }

        sharedPref = MySharedPreferences(this)

        binding.btnToGuest.setOnClickListener {
            val intent = Intent(this, HomePageActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            showToast("Masuk Sebagai Tamu")
        }

        binding.btnToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        if (sharedPref.getUser().token != null) {
            val intent = Intent(this@MainActivity, HomePageActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}