package com.example.teran.ui.journal

import androidx.recyclerview.widget.DiffUtil
import com.example.teran.data.model.Journal

class JournalDiffCallback(private val oldJournalList: List<Journal>, private val newJournalList: List<Journal>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldJournalList.size
    override fun getNewListSize(): Int = newJournalList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldJournalList[oldItemPosition].id == newJournalList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldJournal = oldJournalList[oldItemPosition]
        val newJournal = newJournalList[newItemPosition]
        return oldJournal.mood == newJournal.mood &&
                oldJournal.desc == newJournal.desc &&
                oldJournal.date == newJournal.date
    }
}