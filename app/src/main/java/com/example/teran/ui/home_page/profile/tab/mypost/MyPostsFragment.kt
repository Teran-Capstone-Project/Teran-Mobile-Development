package com.example.teran.ui.home_page.profile.tab.mypost

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.teran.R
import com.example.teran.data.model.Post
import com.example.teran.data.model.PostFavorite
import com.example.teran.data.sharedpref.MySharedPreferences
import com.example.teran.data.viewmodel.ViewModelFactory
import com.example.teran.databinding.FragmentMyPostsBinding
import com.example.teran.databinding.FragmentMyProfileBinding
import com.example.teran.ui.home_page.post.PostViewModel
import com.example.teran.ui.home_page.post.detail.DetailPostActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.card.MaterialCardView

class MyPostsFragment : Fragment(), MyPostAdapter.OnItemClickListener, MyPostAdapter.OnBottomSheetDialog {

    private var _binding: FragmentMyPostsBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPref: MySharedPreferences

    private lateinit var myPostViewModel: MyPostViewModel

    private lateinit var rvMyPosts: RecyclerView
    private lateinit var swipeRefreshMyPostLayout: SwipeRefreshLayout

    private fun setData() {
        sharedPref = MySharedPreferences(requireActivity())
        myPostViewModel = obtainViewModel(requireActivity())
        rvMyPosts = binding.rvMyPosts
        swipeRefreshMyPostLayout = binding.swipeRefreshMyPostLayout
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyPostsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setData()


        myPostViewModel.getAllMyPosts()
        myPostViewModel.myPosts.observe(viewLifecycleOwner) {
            setRecyclerView(it)
            binding.myPostsTitle.text = "${it.size} Posts Created"
            println(it)
        }

        myPostViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        swipeRefreshMyPostLayout.setOnRefreshListener {
            myPostViewModel.getAllMyPosts()
            myPostViewModel.isOnFailure.observe(viewLifecycleOwner) {
                swipeRefreshMyPostLayout.isRefreshing = it
            }
        }

        return root
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.shimmerLoadingPosts.startShimmer()
        } else {
            binding.shimmerLoadingPosts.stopShimmer()
            binding.shimmerLoadingPosts.visibility = View.GONE
            binding.rvMyPosts.visibility = View.VISIBLE
        }
    }

    private fun setRecyclerView(listMyPosts: ArrayList<Post>) {
        listMyPosts.reverse()
        rvMyPosts.layoutManager = LinearLayoutManager(requireActivity())
        rvMyPosts.adapter = MyPostAdapter(listMyPosts, requireActivity(), this, this)
    }

    private fun obtainViewModel(activity: FragmentActivity): MyPostViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[MyPostViewModel::class.java]
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
        myPostViewModel
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
                myPostViewModel.deletePostFavoriteByID(
                    postFavoriteSelected,
                    requireActivity().application
                )
            } else {
                myPostViewModel.addPostToFavorite(
                    postFavoriteSelected,
                    requireActivity().application
                )
            }

            dialog.dismiss()
        }
        deleteBtn.setOnClickListener {
            myPostViewModel.deletePost(post.id, requireActivity())
            dialog.dismiss()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            myPostViewModel.getAllMyPosts()
        }
    }
}