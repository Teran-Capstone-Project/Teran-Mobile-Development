package com.example.teran.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.teran.data.database.Note
import com.example.teran.data.helper.NoteDiffCallback
import com.example.teran.databinding.ListItemJournalBinding
import com.example.teran.ui.journal.AddJournalActivity

class JournalAdapter : RecyclerView.Adapter<JournalAdapter.MyViewHolder>() {
    private val listNotes = ArrayList<Note>()
    class MyViewHolder(private val binding: ListItemJournalBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            with(binding) {
                journalTitlesTV.text = note.title
                journalDatesTV.text=note.date
                journalContentTV.text=note.description
                CVItemJournal.setOnClickListener {
                    val intent = Intent(it.context, AddJournalActivity::class.java)
                    intent.putExtra(AddJournalActivity.EXTRA_NOTE, note)
                    it.context.startActivity(intent)
                }
            }
        }
    }


    fun setListNotes(listNotes: List<Note>) {
        val diffCallback = NoteDiffCallback(this.listNotes, listNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listNotes.clear()
        this.listNotes.addAll(listNotes)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListItemJournalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listNotes[position])
    }

    override fun getItemCount(): Int {
        return listNotes.size
    }

}