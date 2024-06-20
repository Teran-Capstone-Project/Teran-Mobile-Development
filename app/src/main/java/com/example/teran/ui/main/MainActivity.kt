package com.example.teran.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
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

    private lateinit var viewPager: ViewPager
    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var sliderList: ArrayList<SliderData>

    private fun setData() {
        sharedPref = MySharedPreferences(this)

        viewPager = binding.mainViewPager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setData()

        supportActionBar?.hide()

        setSlider()

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

    private fun setSlider() {
        sliderList = ArrayList()
        sliderList.add(
            SliderData(
                "Journal",
                "Aplikasi ini menawarkan ruang pribadi untuk mencatat setiap pengalaman, refleksi, dan tujuan harianmu.",
                R.drawable.illustration_journal
            )
        )
        sliderList.add(
            SliderData(
                "Meditation",
                "Menyediakan timer yang dapat disesuaikan dan musik menenangkan untuk mendukung sesi meditasimu.",
                R.drawable.illustration_meditation
            )
        )
        sliderList.add(
            SliderData(
                "Survey",
                "Menyediakan survei singkat dengan pertanyaan yang dirancang untuk mengukur tingkat stresmu dan memberikan probabilitas hasil.",
                R.drawable.illustration_survey
            )
        )
        sliderList.add(
            SliderData(
                "Connection",
                "Bagikan pengalaman dan dukunganmu dengan membuat postingan yang bisa dikenali atau anonim, memberikan komentar, dan menyukai postingan orang lain.",
                R.drawable.illustration_connection
            )
        )

        sliderAdapter = SliderAdapter(this, sliderList)
        viewPager.adapter = sliderAdapter

        val viewListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                //
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        binding.dotSlide1.setTextColor(resources.getColor(R.color.orange))
                        binding.dotSlide2.setTextColor(resources.getColor(R.color.shimmer))
                        binding.dotSlide3.setTextColor(resources.getColor(R.color.shimmer))
                        binding.dotSlide4.setTextColor(resources.getColor(R.color.shimmer))
                    }
                    1 -> {
                        binding.dotSlide1.setTextColor(resources.getColor(R.color.shimmer))
                        binding.dotSlide2.setTextColor(resources.getColor(R.color.orange))
                        binding.dotSlide3.setTextColor(resources.getColor(R.color.shimmer))
                        binding.dotSlide4.setTextColor(resources.getColor(R.color.shimmer))
                    }
                    2 -> {
                        binding.dotSlide1.setTextColor(resources.getColor(R.color.shimmer))
                        binding.dotSlide2.setTextColor(resources.getColor(R.color.shimmer))
                        binding.dotSlide3.setTextColor(resources.getColor(R.color.orange))
                        binding.dotSlide4.setTextColor(resources.getColor(R.color.shimmer))
                    }
                    3 -> {
                        binding.dotSlide1.setTextColor(resources.getColor(R.color.shimmer))
                        binding.dotSlide2.setTextColor(resources.getColor(R.color.shimmer))
                        binding.dotSlide3.setTextColor(resources.getColor(R.color.shimmer))
                        binding.dotSlide4.setTextColor(resources.getColor(R.color.orange))
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                //
            }

        }

        viewPager.addOnPageChangeListener(viewListener)
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