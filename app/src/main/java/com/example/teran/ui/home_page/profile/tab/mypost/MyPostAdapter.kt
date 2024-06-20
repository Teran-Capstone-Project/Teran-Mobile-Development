package com.example.teran.ui.home_page.profile.tab.mypost

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.teran.R
import com.example.teran.data.helper.DateHelper.formatIsoToIndonesianDate
import com.example.teran.data.helper.SvgImageLoader
import com.example.teran.data.model.Post
import com.example.teran.databinding.ItemPostBinding

class MyPostAdapter(
    private val postsItem: ArrayList<Post>,
    val context: Context,
    private val itemClickListener: OnItemClickListener,
    private val bottomSheetDialog: OnBottomSheetDialog
) : RecyclerView.Adapter<MyPostAdapter.ListViewHolder>() {
    interface OnItemClickListener {
        fun onItemClickListenerClick(post: Post)
    }
    interface OnBottomSheetDialog {
        fun onBottomSheetDialogClick(post: Post)
    }

    inner class ListViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            with(binding) {
                SvgImageLoader(context)
                    .loadSvgImage(post.profilePictureURL, profilePicturePost)

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

                usernamePost.setOnClickListener {
                    println(post)
                }

                morePostBtn.setOnClickListener {
                    bottomSheetDialog.onBottomSheetDialogClick(post)
                    true
                }

                itemPostCard.setOnClickListener {
                    itemClickListener.onItemClickListenerClick(post)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val postItem = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(postItem)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(postsItem[position])
    }

    override fun getItemCount(): Int = postsItem.size
}