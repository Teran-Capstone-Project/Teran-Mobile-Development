package com.example.teran.ui.home_page.post.favorite

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.teran.R
import com.example.teran.data.helper.DateHelper.formatIsoToIndonesianDate
import com.example.teran.data.helper.SvgImageLoader
import com.example.teran.data.model.Favorite
import com.example.teran.databinding.ItemPostBinding
import com.example.teran.ui.home_page.post.detail.DetailPostActivity

class FavoritePostAdapter(val favoriteItem: MutableList<Favorite> = mutableListOf(),
   val context: Context) : RecyclerView.Adapter<FavoritePostAdapter.ListViewHolder>() {
    inner class ListViewHolder(val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite) {
            with(binding) {
                SvgImageLoader(context)
                    .loadSvgImage(favorite.profilePictureURL, profilePicturePost)

                if (favorite.isAnonim) {
                    usernamePost.text = "Anonim"
                    usernamePost.setTextColor(ContextCompat.getColor(context, R.color.orange))
                } else {
                    usernamePost.text = favoriteItem[position].username
                    usernamePost.setTextColor(ContextCompat.getColor(context, R.color.black))
                }

                descPost.text = favorite.desc
                commentsSizePost.text = "${favorite.commentsSize} Comments"
                datePost.text = formatIsoToIndonesianDate(favorite.date)

                itemPostCard.setOnClickListener {
                    val intent = Intent(context, DetailPostActivity::class.java)
                    intent.putExtra(DetailPostActivity.EXTRA_POST, favorite)
                    it.context.startActivity(intent)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val item = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(item)
    }

    override fun getItemCount(): Int = favoriteItem.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(favoriteItem[position])
    }
}

