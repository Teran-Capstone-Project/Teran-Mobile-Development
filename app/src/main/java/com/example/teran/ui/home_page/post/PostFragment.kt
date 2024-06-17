package com.example.teran.ui.home_page.post

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teran.data.api.response.post.PostsItem
import com.example.teran.data.model.Post
import com.example.teran.data.sharedpref.MySharedPreferences
import com.example.teran.data.viewmodel.ViewModelFactory
import com.example.teran.databinding.FragmentPostBinding

class PostFragment : Fragment() {

    private var _binding: FragmentPostBinding? = null

    private val binding get() = _binding!!

    private lateinit var sharedPref: MySharedPreferences
    private lateinit var postViewModel: PostViewModel
    private lateinit var rvPosts: RecyclerView
    private var listPosts = ArrayList<Post>()

    private fun setData() {
        rvPosts = binding.rvPosts
        postViewModel = obtainViewModel(requireActivity())
        sharedPref = MySharedPreferences(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPostBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setData()

        postViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        postViewModel.listPost.observe(viewLifecycleOwner) {
            setPostsData(it)
            setRecyclerView()
        }

        binding.fabPost.setOnClickListener {
            val intent = Intent(requireActivity(), AddPostActivity::class.java)
            startActivity(intent)
        }

        return root
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
                    postsItemApi.comments.size,
                    postsItemApi.createdAt
                )
            )
        }
    }

    private fun setRecyclerView() {
        rvPosts.layoutManager = LinearLayoutManager(requireActivity())
        val postsAdapter = PostsAdapter(listPosts, requireActivity())
        rvPosts.adapter = postsAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            requireActivity().window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            binding.shimmerLoadingPosts.startShimmer()
        } else {
            requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            binding.shimmerLoadingPosts.stopShimmer()
            binding.shimmerLoadingPosts.visibility = View.GONE
            binding.rvPosts.visibility = View.VISIBLE
        }
    }

    private fun obtainViewModel(activity: FragmentActivity): PostViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[PostViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        println("onResume")
        postViewModel.getAllPosts()
    }

}