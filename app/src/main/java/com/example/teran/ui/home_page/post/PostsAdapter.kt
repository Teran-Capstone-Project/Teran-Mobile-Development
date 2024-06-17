package com.example.teran.ui.home_page.post

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.teran.R
import com.example.teran.data.helper.DateHelper.formatIsoToIndonesianDate
import com.example.teran.data.model.Post
import com.example.teran.databinding.ItemPostBinding

class PostsAdapter(private val postsItem: ArrayList<Post>, val context: Context) : RecyclerView.Adapter<PostsAdapter.ListViewHolder>() {

    inner class ListViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            with(binding) {
                val imageLoader = ImageLoader.Builder(context)
                    .components {
                        add(SvgDecoder.Factory())
                    }
                    .build()
                val imageRequest = ImageRequest.Builder(context)
                    .data(post.profilePictureURL)
                    .target(profilePicturePost)
                    .build()
                imageLoader.enqueue(imageRequest)

                if (post.isAnonim) {
                    usernamePost.text = "Anonim"
                    usernamePost.setTextColor(ContextCompat.getColor(context, R.color.orange))
                } else {
                    usernamePost.text = postsItem[position].username
                    usernamePost.setTextColor(ContextCompat.getColor(context, R.color.black))
                }

                descPost.text = post.desc
                commentsSizePost.text = "${post.commentsSize} Comments"
                datePost.text = formatIsoToIndonesianDate(post.date)

                itemPostCard.setOnClickListener {
                    println(post)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val postItem = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(postItem)
    }

    override fun getItemCount(): Int = postsItem.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(postsItem[position])
    }
}