package com.example.teran.ui.home_page.post

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.teran.R
import com.example.teran.data.api.response.post.PostsItem
import com.example.teran.data.model.Post
import com.example.teran.data.model.PostFavorite
import com.example.teran.data.sharedpref.MySharedPreferences
import com.example.teran.data.viewmodel.ViewModelFactory
import com.example.teran.databinding.FragmentPostBinding
import com.example.teran.ui.home_page.post.detail.DetailPostActivity
import com.example.teran.ui.home_page.post.favorite.PostFavoriteActivity
import com.example.teran.ui.login.LoginActivity
import com.example.teran.ui.register.RegisterActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.card.MaterialCardView

class PostFragment : Fragment(),
    PostsAdapter.OnBottomSheetDialog,
    PostsAdapter.OnItemClickListener {

    private var _binding: FragmentPostBinding? = null

    private val binding get() = _binding!!

    private lateinit var sharedPref: MySharedPreferences

    private lateinit var postViewModel: PostViewModel
    private var listPosts = ArrayList<Post>()
    private var isDataLoaded = false

    private lateinit var rvPosts: RecyclerView
    private lateinit var swipeRefreshPostLayout: SwipeRefreshLayout

    private fun setData() {
        sharedPref = MySharedPreferences(requireActivity())
        rvPosts = binding.rvPosts
        postViewModel = obtainViewModel(requireActivity())
        swipeRefreshPostLayout = binding.swipeRefreshPostLayout
    }

    override fun onResume() {
        super.onResume()
        if (!isDataLoaded) {
            postViewModel.getAllPosts(requireActivity())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPostBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (!isDataLoaded) {
            setData()
        }

        if (sharedPref.getUser().token != null) {
            setHasOptionsMenu(true)

            binding.swipeRefreshPostLayout.visibility = View.VISIBLE

            postViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
            postViewModel.listPost.observe(viewLifecycleOwner) {
                setPostsData(it)
                setRecyclerView()
                isDataLoaded = true
            }

            binding.fabPost.setOnClickListener {
                val intent = Intent(requireActivity(), AddPostActivity::class.java)
                startActivityForResult(intent, 1)
            }

            swipeRefreshPostLayout.setOnRefreshListener {
                postViewModel.getAllPosts(requireActivity())
                postViewModel.isOnFailure.observe(viewLifecycleOwner) {
                    swipeRefreshPostLayout.isRefreshing = it
                }
            }

        } else {
            binding.displayPostGuest.visibility = View.VISIBLE
            setLoginBtn()
            setRegisterBtn()
        }

        return root
    }

    private fun setLoginBtn() {
        binding.postLoginBtn.setOnClickListener {
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setRegisterBtn() {
        binding.postRegisterBtn.setOnClickListener {
            val intent = Intent(requireActivity(), RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setPostsData(listPostsApi: List<PostsItem>) {
        listPosts.clear()
        listPostsApi.forEach { postsItemApi ->
            listPosts.add(
                Post(
                    postsItemApi.id,
                    postsItemApi.description,
                    postsItemApi.user.name,
                    postsItemApi.user.profilePicture,
                    postsItemApi.anonim,
                    postsItemApi.user.id,
                    postsItemApi.comments.size,
                    postsItemApi.createdAt
                )
            )
        }
    }

    private fun setRecyclerView() {
        rvPosts.layoutManager = LinearLayoutManager(requireActivity())
        val postsAdapter = PostsAdapter(listPosts, requireActivity(), this, this)
        rvPosts.adapter = postsAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.shimmerLoadingPosts.startShimmer()
        } else {
            requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            binding.shimmerLoadingPosts.stopShimmer()
            binding.shimmerLoadingPosts.visibility = View.GONE
            binding.rvPosts.visibility = View.VISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            println(resultCode)
            postViewModel.getAllPosts(requireActivity())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_appbar_post, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuFavoritePost -> {
                val intent = Intent(requireActivity(), PostFavoriteActivity::class.java)
                startActivityForResult(intent, 1)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun obtainViewModel(activity: FragmentActivity): PostViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[PostViewModel::class.java]
    }

    override fun onItemClickListenerClick(post: Post) {
        val intent = Intent(requireActivity(), DetailPostActivity::class.java)
        intent.putExtra(DetailPostActivity.EXTRA_POST, post)
        startActivityForResult(intent, 1)
    }

    override fun onBottomSheetDialogClick(post: Post) {
        val dialog = BottomSheetDialog(requireActivity())
        val view = layoutInflater.inflate(R.layout.bottom_sheet_post, null)
        dialog.setContentView(view)
        dialog.show()

        val favoriteBtn = view.findViewById<MaterialCardView>(R.id.favoriteBottomSheetPostBtn)
        val deleteBtn = view.findViewById<MaterialCardView>(R.id.deleteBottomSheetPostBtn)

        if(post.userID != sharedPref.getUser().id) {
            deleteBtn.visibility = View.GONE
        }

        var isFavoritePost = false
        postViewModel
            .searchPostFavoriteByID(post.id, requireActivity().application)
            .observe(viewLifecycleOwner) { favoritePost ->
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
                post.id,
                post.desc,
                post.username,
                post.profilePictureURL,
                post.isAnonim,
                post.userID,
                post.commentsSize,
                post.date
            )
            if (isFavoritePost) {
                postViewModel.deletePostFavoriteByID(
                    postFavoriteSelected,
                    requireActivity().application
                )
            } else {
                postViewModel.addPostToFavorite(
                    postFavoriteSelected,
                    requireActivity().application
                )
            }

            dialog.dismiss()
        }
        deleteBtn.setOnClickListener {
            postViewModel.deletePost(post.id, requireActivity())
            dialog.dismiss()
        }
    }
}