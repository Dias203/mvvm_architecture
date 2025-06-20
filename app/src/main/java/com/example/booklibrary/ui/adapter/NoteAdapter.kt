package com.example.notetakingapp.adapter

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.booklibrary.R
import com.example.booklibrary.data.model.Note
import com.example.booklibrary.databinding.NoteLayoutBinding
import com.example.booklibrary.ui.view.screens.UpdateNoteActivity
import java.util.Random

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(val itemBinding: NoteLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id && oldItem.noteBody == newItem.noteBody && oldItem.noteTitle == newItem.noteTitle
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)
    var onDeleteNote: ((Note) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            NoteLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = differ.currentList[position]

        holder.itemBinding.tvNoteTitle.text = currentNote.noteTitle
        holder.itemBinding.tvNoteBody.text = currentNote.noteBody

        val random = Random()
        val color = Color.argb(
            255,
            random.nextInt(256),
            random.nextInt(256),
            random.nextInt(256)
        )

        holder.itemBinding.ibColor.setBackgroundColor(color)
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, UpdateNoteActivity::class.java).apply {
                putExtra("note", currentNote)
            }
            it.context.startActivity(intent)
        }

        /*holder.itemBinding.checkboxSelect.setOnCheckedChangeListener(null)
        holder.itemBinding.checkboxSelect.isChecked = currentNote.isSelected
        holder.itemBinding.checkboxSelect.setOnCheckedChangeListener { _, isChecked ->
            currentNote.isSelected = isChecked
        }*/

        holder.itemBinding.btnMore.setOnClickListener {
            showPopupMenu(it, currentNote)
        }
    }

    private fun showPopupMenu(view: View, currentNote: Note) {
        val popup = PopupMenu(view.context, view)
        popup.menuInflater.inflate(R.menu.menu_note, popup.menu)
        popup.setOnMenuItemClickListener { item ->
            when(item.itemId) {
                R.id.action_delete -> {
                    onDeleteNote?.let { it(currentNote) }
                    true
                }
                R.id.action_edit -> {
                    val intent = Intent(view.context, UpdateNoteActivity::class.java).apply {
                        putExtra("note", currentNote)
                    }
                    view.context.startActivity(intent)
                    true
                }
                else -> false
            }
        }
        popup.show()
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun getAllNotesSelected() : List<Note> {
        return differ.currentList.filter { it.isSelected }
    }

    fun selectAll(isSelected: Boolean) {
        val updatedList = differ.currentList.map {
            it.copy(isSelected = isSelected)
        }
        differ.submitList(updatedList)
    }
}