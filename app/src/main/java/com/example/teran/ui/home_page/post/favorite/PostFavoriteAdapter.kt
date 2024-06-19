package com.example.teran.ui.home_page.post.favorite

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.teran.R
import com.example.teran.data.helper.DateHelper.formatIsoToIndonesianDate
import com.example.teran.data.helper.SvgImageLoader
import com.example.teran.data.model.Post
import com.example.teran.data.model.PostFavorite
import com.example.teran.databinding.ItemPostBinding
import com.example.teran.ui.home_page.post.detail.DetailPostActivity

class PostFavoriteAdapter(
    private val postsFavoritesItem: List<PostFavorite>,
    val context: Context,
    private val itemClickListener: OnItemClickListener,
    private val bottomSheetDialog: OnBottomSheetDialog
) : RecyclerView.Adapter<PostFavoriteAdapter.ListViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(postFavorite: PostFavorite)
    }
    interface OnBottomSheetDialog {
        fun onBottomSheetDialogClick(post: PostFavorite)
    }

    inner class ListViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(postFavorite: PostFavorite) {
            with(binding) {
                SvgImageLoader(context)
                    .loadSvgImage(postFavorite.profilePictureURL, profilePicturePost)

                if (postFavorite.isAnonim!!) {
                    usernamePost.text = "Anonim"
                    usernamePost.setTextColor(ContextCompat.getColor(context, R.color.orange))
                } else {
                    usernamePost.text = postsFavoritesItem[position].username
                    usernamePost.setTextColor(ContextCompat.getColor(context, R.color.black))
                }

                descPost.text = postFavorite.desc
                commentsSizePost.visibility = View.GONE
                commentImageView.visibility = View.GONE
                datePost.text = formatIsoToIndonesianDate(postFavorite.date!!)

                morePostBtn.setOnClickListener {
                    bottomSheetDialog.onBottomSheetDialogClick(postFavorite)
                    true
                }

                itemPostCard.setOnClickListener {
                    itemClickListener.onItemClick(postFavorite)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val postItem = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(postItem)
    }

    override fun getItemCount(): Int = postsFavoritesItem.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(postsFavoritesItem[position])
    }
}