package com.example.teran.ui.home_page.post.favorite

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teran.data.model.Favorite
import com.example.teran.data.viewmodel.FavoriteViewModelFactory
import com.example.teran.data.viewmodel.ViewModelFactory
import com.example.teran.databinding.ActivityFavoritePostBinding

class FavoritePostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritePostBinding
    private val viewModel: FavoritePostVM by viewModels {
        FavoriteViewModelFactory.getInstance(application)
    }
    private lateinit var adapter: FavoritePostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            elevation = 0.0f
            title = "Favorite"
            setDisplayHomeAsUpEnabled(true)
        }

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = FavoritePostAdapter(context = this)
        binding.rvFavourite.layoutManager = LinearLayoutManager(this)
        binding.rvFavourite.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.getFavorite().observe(this) { favorites ->
            adapter.favoriteItem.clear()
            adapter.favoriteItem.addAll(favorites)
            adapter.notifyDataSetChanged()
        }
    }
}
