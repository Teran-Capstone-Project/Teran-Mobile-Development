package com.example.teran.ui.home_page.post.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.teran.data.api.ApiConfig
import com.example.teran.data.api.response.post.GetPostResponse
import com.example.teran.data.helper.SvgImageLoader
import com.example.teran.data.model.Post
import com.example.teran.data.sharedpref.MySharedPreferences
import com.example.teran.data.viewmodel.ViewModelFactory
import com.example.teran.databinding.ActivityDetailPostBinding
import com.example.teran.ui.home_page.post.PostViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPostBinding

    private lateinit var sharedPref: MySharedPreferences

    private lateinit var detailPostVM: DetailPostVM

    private var post: Post? = null

    companion object {
        const val EXTRA_POST = "extra_post"
    }

    private fun setData() {
        detailPostVM = obtainViewModel(this@DetailPostActivity)
        sharedPref = MySharedPreferences(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "Detail Post"
            elevation = 0f
        }

        setData()

        post = intent.getParcelableExtra(EXTRA_POST)
        setDetailPost()
    }

    private fun setDetailPost() {
        SvgImageLoader(this)
            .loadSvgImage(post!!.profilePictureURL, binding.profilePictureDetailPost)

        binding.usernameDetailPost.text = post!!.username
        binding.descDetailPost.text = post!!.desc
        binding.commentsSizeDetailPost.text = "${post!!.commentsSize} Comments"
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailPostVM {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailPostVM::class.java]
    }
}