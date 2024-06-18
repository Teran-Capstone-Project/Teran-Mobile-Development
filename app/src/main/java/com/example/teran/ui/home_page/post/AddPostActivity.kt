package com.example.teran.ui.home_page.post

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.teran.R
import com.example.teran.data.api.ApiConfig
import com.example.teran.data.api.request.AddPostRequest
import com.example.teran.data.api.response.post.AddPostResponse
import com.example.teran.data.sharedpref.MySharedPreferences
import com.example.teran.data.viewmodel.ViewModelFactory
import com.example.teran.databinding.ActivityAddPostBinding
import com.example.teran.ui.home_page.HomePageActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddPostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPostBinding

    private lateinit var sharedPref: MySharedPreferences

    private var isAnonimPost: Boolean = false

    private fun setData() {
        sharedPref = MySharedPreferences(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "Tambah Post"
            elevation = 0f
        }

        setData()

        binding.isAnonimSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            isAnonimPost = isChecked
        }

        binding.addPostBtn.setOnClickListener {
            addPost()
        }
    }

    private fun addPost() {
        ApiConfig.getApiService().addPost(
            "Bearer ${sharedPref.getUser().token}",
            AddPostRequest(
                binding.descPostEdtText.text.toString(),
                isAnonimPost
            )
            ).enqueue(object : Callback<AddPostResponse> {
            override fun onResponse(call: Call<AddPostResponse>, response: Response<AddPostResponse>) {
                val responseBody = response.body()
                if (responseBody != null) {
                    finish()
                } else {
                    println("onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<AddPostResponse>, t: Throwable) {
                println("onFailure: ${t.message}")
            }
        })
    }
}