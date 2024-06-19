package com.example.teran.ui.home_page.post.detail

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.teran.R
import com.example.teran.data.helper.DateHelper.formatIsoToIndonesianTime
import com.example.teran.data.helper.SvgImageLoader
import com.example.teran.data.model.Post
import com.example.teran.data.model.PostComment
import com.example.teran.data.sharedpref.MySharedPreferences
import com.example.teran.databinding.ItemPostCommentBinding

class PostCommentsAdapter(
    private val postComment: List<PostComment>,
    private val post: Post,
    private val context: Context,
    private val longClickListener: OnCommentLongClickListener,
) : RecyclerView.Adapter<PostCommentsAdapter.ListViewHolder>() {
    interface OnCommentLongClickListener {
        fun onCommentLongClick(postComment: PostComment)
    }

    inner class ListViewHolder(val binding: ItemPostCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(postComment: PostComment, longClickListener: OnCommentLongClickListener) {
            with(binding) {
                SvgImageLoader(context)
                    .loadSvgImage(postComment.profilePicture, profilePicturePostComment)

                if (post.userID == postComment.userId && post.isAnonim) {
                    usernamePostComment.text = "Anonim"
                    usernamePostComment.setTextColor(ContextCompat.getColor(context, R.color.orange))
                } else {
                    usernamePostComment.text = postComment.username
                    usernamePostComment.setTextColor(ContextCompat.getColor(context, R.color.black))
                }
                descPostComment.text = postComment.comment
                datePostComment.text = formatIsoToIndonesianTime(postComment.date)

                val sharedPref: MySharedPreferences = MySharedPreferences(context)

                // Delete Post
                if (postComment.userId == sharedPref.getUser().id) {
                        itemPostCommentCard.setOnLongClickListener {
                            longClickListener.onCommentLongClick(postComment)
                            true
                        }
                } else {
                    itemPostCommentCard.setOnLongClickListener(null)
                }

                itemPostCommentCard.setOnClickListener {
                    // Do splash effect
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val postComment = ItemPostCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(postComment)
    }

    override fun getItemCount(): Int = postComment.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(postComment[position], longClickListener)
    }
}
