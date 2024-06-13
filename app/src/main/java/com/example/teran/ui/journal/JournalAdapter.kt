package com.example.teran.ui.journal

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.teran.data.model.Journal
import com.example.teran.databinding.ItemJournalBinding

class JournalAdapter : RecyclerView.Adapter<JournalAdapter.JournalViewHolder>() {

    private val listJournals = ArrayList<Journal>()

    fun setListJournals(listJournals: List<Journal>) {
        val diffCallback = JournalDiffCallback(this.listJournals, listJournals)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listJournals.clear()
        this.listJournals.addAll(listJournals)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class JournalViewHolder(private val binding: ItemJournalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(journal: Journal) {
            with(binding) {
                if (journal.mood == null) {
                    journalMood.visibility = View.GONE
                } else {
                    journalMood.visibility = View.VISIBLE
                    journalMood.text = journal.mood
                }
                journalDesc.text = journal.desc
                journalDate.text = journal.date
                itemJournalCard.setOnClickListener {
                    val intent = Intent(it.context, EditJournalActivity::class.java)
                    intent.putExtra(EditJournalActivity.EXTRA_JOURNAL, journal)
                    it.context.startActivity(intent)

                    (it.context as JournalActivity).recreate()
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): JournalAdapter.JournalViewHolder {
        val binding = ItemJournalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JournalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JournalAdapter.JournalViewHolder, position: Int) {
        holder.bind(listJournals[position])
    }

    override fun getItemCount(): Int = listJournals.size

}