package com.example.teran.ui.home_page.post.detail

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teran.R
import com.example.teran.data.api.ApiConfig
import com.example.teran.data.api.response.post.GetAllPostsResponse
import com.example.teran.data.api.response.post.GetPostResponse
import com.example.teran.data.helper.DateHelper
import com.example.teran.data.helper.DateHelper.formatIsoToIndonesianDate
import com.example.teran.data.helper.SvgImageLoader
import com.example.teran.data.model.Post
import com.example.teran.data.model.PostComment
import com.example.teran.data.model.PostFavorite
import com.example.teran.data.sharedpref.MySharedPreferences
import com.example.teran.data.viewmodel.ViewModelFactory
import com.example.teran.databinding.ActivityDetailPostBinding
import com.example.teran.ui.home_page.post.PostViewModel
import com.example.teran.ui.home_page.post.PostsAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.card.MaterialCardView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPostActivity : AppCompatActivity(), PostCommentsAdapter.OnCommentLongClickListener {

    private lateinit var binding: ActivityDetailPostBinding

    private lateinit var sharedPref: MySharedPreferences

    private lateinit var detailPostVM: DetailPostVM

    private lateinit var rvPostComment: RecyclerView
    private var post: Post? = null

    companion object {
        const val EXTRA_POST = "extra_post"
    }

    private fun setData() {
        detailPostVM = obtainViewModel(this@DetailPostActivity)
        sharedPref = MySharedPreferences(this)
        post = intent.getParcelableExtra(EXTRA_POST)
        rvPostComment = binding.rvPostComments
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setData()

        checkPostIsNotNull()

        supportActionBar?.apply {
            title = "Detail Post"
            elevation = 0f
            setDisplayHomeAsUpEnabled(true)
        }

        setDetailPost()
        detailPostVM.getAllPostComments(post!!.id)
        detailPostVM.listPostComments.observe(this) {
            setRecyclerView(it)
            binding.commentsSizeDetailPost.text = "${it.size} Comments"
        }

        setAddPostComment()

        binding.moreDetailPostBtn.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.bottom_sheet_post, null)
            dialog.setContentView(view)
            dialog.show()

            val favoriteBtn = view.findViewById<MaterialCardView>(R.id.favoriteBottomSheetPostBtn)
            val deleteBtn = view.findViewById<MaterialCardView>(R.id.deleteBottomSheetPostBtn)

            if(post!!.userID != sharedPref.getUser().id) {
                deleteBtn.visibility = View.GONE
            }

            var isFavoritePost = false

            detailPostVM
                .searchPostFavoriteByID(post!!.id, this.application)
                .observe(this) { favoritePost ->
                if (favoritePost == null) {
                    view.findViewById<ImageView>(R.id.favoriteBorderIcon).visibility = View.VISIBLE
                    view.findViewById<TextView>(R.id.favoriteTextView).setText("Add Favorite")
                    isFavoritePost = false
                } else {
                    view.findViewById<ImageView>(R.id.favoriteFilledIcon).visibility = View.VISIBLE
                    view.findViewById<TextView>(R.id.favoriteTextView).setText("Remove Favorite")
                    isFavoritePost = true
                }
            }

            favoriteBtn.setOnClickListener {
                val postFavoriteSelected: PostFavorite = PostFavorite(
                    post!!.id,
                    post!!.desc,
                    post!!.username,
                    post!!.profilePictureURL,
                    post!!.isAnonim,
                    post!!.userID,
                    post!!.commentsSize,
                    post!!.date
                )
                if (isFavoritePost) {
                    detailPostVM.deletePostFavoriteByID(
                        postFavoriteSelected,
                        this.application
                    )
                } else {
                    detailPostVM.addPostToFavorite(
                        postFavoriteSelected,
                        this.application
                    )
                }
                dialog.dismiss()
            }
            deleteBtn.setOnClickListener {
                detailPostVM.deletePost(post!!.id, this)
                setResult(Activity.RESULT_OK)
                dialog.dismiss()
                finish()
            }
        }
    }

    private fun checkPostIsNotNull() {
        ApiConfig.getApiService().getAllPostComments(
            "Bearer ${sharedPref.getUser().token}",
            post!!.id
        ).enqueue(object : Callback<GetPostResponse> {
            override fun onResponse(p0: Call<GetPostResponse>, response: Response<GetPostResponse>) {
                if (response.code() == 404) {
                    binding.addCommentLayout.visibility = View.GONE
                    binding.textView6.visibility = View.GONE
                    showToast("Postingan Ini Telah Dihapus")
                }
            }

            override fun onFailure(p0: Call<GetPostResponse>, p1: Throwable) {
                showToast("Gagal Memuat Postingan")
            }
        })
    }

    private fun setAddPostComment() {
        binding.addCommentPostBtn.setOnClickListener {
            if (binding.commentEdtText.text!!.isNotEmpty()) {
                detailPostVM.addCommentPost(post!!.id, binding.commentEdtText.text.toString())
                binding.commentEdtText.text!!.clear()

                binding.commentEdtLayout.isErrorEnabled = false
                binding.commentEdtLayout.helperText = null
                binding.commentEdtLayout.error = null

            } else {
                binding.commentEdtLayout.error = "Mohon isi komentar ya!"
            }
        }
    }

    private fun setDetailPost() {
        SvgImageLoader(this)
            .loadSvgImage(post!!.profilePictureURL, binding.profilePictureDetailPost)

        if (post!!.isAnonim) {
            binding.usernameDetailPost.text = "Anonim"
            binding.usernameDetailPost.setTextColor(ContextCompat.getColor(this, R.color.orange))
        } else {
            binding.usernameDetailPost.text = post!!.username
        }
        binding.descDetailPost.text = post!!.desc
        binding.commentsSizeDetailPost.text = "${post!!.commentsSize} Comments"
        binding.dateDetailPost.text = formatIsoToIndonesianDate(post!!.date)
    }

    private fun setRecyclerView(listPostComments: List<PostComment>) {
        rvPostComment.layoutManager = LinearLayoutManager(this)
        val postCommentsAdapter = PostCommentsAdapter(listPostComments, post!!, this, this)
        rvPostComment.adapter = postCommentsAdapter
    }

    override fun onCommentLongClick(postComment: PostComment) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Hapus Pesan?")
        builder.setMessage("Hapus komentar: ${postComment.comment}")
        builder.setPositiveButton("Hapus") { dialog, which ->
            detailPostVM.deleteComment(postComment.commentId, post!!.id)
            showToast("Berhasil hapus komentar")
        }
        val dialog = builder.create()
        dialog.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailPostVM {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailPostVM::class.java]
    }
}
