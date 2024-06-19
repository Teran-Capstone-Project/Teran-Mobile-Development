package com.example.teran.ui.home_page.post.favorite

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teran.R
import com.example.teran.data.api.ApiConfig
import com.example.teran.data.model.Post
import com.example.teran.data.model.PostFavorite
import com.example.teran.data.sharedpref.MySharedPreferences
import com.example.teran.data.viewmodel.ViewModelFactory
import com.example.teran.databinding.ActivityPostFavoriteBinding
import com.example.teran.ui.home_page.post.PostViewModel
import com.example.teran.ui.home_page.post.detail.DetailPostActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.card.MaterialCardView

class PostFavoriteActivity : AppCompatActivity(),
    PostFavoriteAdapter.OnBottomSheetDialog,
    PostFavoriteAdapter.OnItemClickListener{

    private lateinit var binding: ActivityPostFavoriteBinding

    private lateinit var postFavoriteVM: PostFavoriteVM

    private lateinit var rvPostsFavorite: RecyclerView

    private lateinit var sharedPref: MySharedPreferences

    private fun setData() {
        sharedPref = MySharedPreferences(this)
        postFavoriteVM = obtainViewModel(this)
        rvPostsFavorite = binding.rvPostsFavorites
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setData()

        supportActionBar?.apply {
            title = "Favorite Post"
            elevation = 0.0f
            setDisplayHomeAsUpEnabled(true)
        }

        postFavoriteVM.getAllPostsFavorites().observe(this) { listPostFavorites ->
            if (listPostFavorites.isNotEmpty()) {
                setRecyclerView(listPostFavorites)
                binding.titlePostFavorite.setText("${listPostFavorites.size} Liked Posts")
                binding.likedPostDisplay.visibility = View.VISIBLE
            } else {
                binding.notLikedPostDisplay.visibility = View.VISIBLE
                binding.likedPostDisplay.visibility = View.GONE
            }
        }
    }

    private fun setRecyclerView(listPostsFavorites: List<PostFavorite>) {
        rvPostsFavorite.layoutManager = LinearLayoutManager(this)
        rvPostsFavorite.adapter = PostFavoriteAdapter(
            listPostsFavorites,
            this,
            this,
            this
        )
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

    private fun obtainViewModel(activity: AppCompatActivity): PostFavoriteVM {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[PostFavoriteVM::class.java]
    }

    override fun onBottomSheetDialogClick(postFavorite: PostFavorite) {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_post, null)
        dialog.setContentView(view)
        dialog.show()

        val favoriteBtn = view.findViewById<MaterialCardView>(R.id.favoriteBottomSheetPostBtn)
        val deleteBtn = view.findViewById<MaterialCardView>(R.id.deleteBottomSheetPostBtn)

        if(postFavorite.userID != sharedPref.getUser().id) {
            deleteBtn.visibility = View.GONE
        }

        var isFavoritePost = false
        postFavoriteVM
            .searchPostFavoriteByID(postFavorite.postID!!)
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
                postFavorite.postID,
                postFavorite.desc,
                postFavorite.username,
                postFavorite.profilePictureURL,
                postFavorite.isAnonim,
                postFavorite.userID,
                postFavorite.commentsSize,
                postFavorite.date
            )
            if (isFavoritePost) {
                postFavoriteVM.deletePostFavoriteByID(
                    postFavoriteSelected,
                )
            } else {
                postFavoriteVM.addPostToFavorite(
                    postFavoriteSelected,
                    )
            }

            dialog.dismiss()
        }
        deleteBtn.setOnClickListener {
            postFavoriteVM.deletePostFavoriteByID(postFavorite)
            postFavoriteVM.deletePost(postFavorite.postID, this)
            dialog.dismiss()
            setResult(Activity.RESULT_OK)
        }
    }

    override fun onItemClick(postFavorite: PostFavorite) {
        val intent = Intent(this, DetailPostActivity::class.java)
        val post = Post(
            postFavorite.postID,
            postFavorite.desc!!,
            postFavorite.username!!,
            postFavorite.profilePictureURL,
            postFavorite.isAnonim!!,
            postFavorite.userID!!,
            postFavorite.commentsSize!!,
            postFavorite.date!!
        )
        intent.putExtra(DetailPostActivity.EXTRA_POST, post)
        startActivity(intent)
    }
}