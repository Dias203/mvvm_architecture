package com.example.booklibrary.ui.view.extensions

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.booklibrary.data.model.Note
import com.example.booklibrary.ui.view.screens.MainActivity
import com.example.booklibrary.ui.view.screens.NewNoteActivity

fun MainActivity.setUI() {
    binding.recyclerView.apply {
        layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        setHasFixedSize(true)
        adapter = noteAdapter
    }

    noteViewModel.getAllNotes().observe(this) { note ->
        noteAdapter.differ.submitList(note)
        updateUI(note)
    }
    binding.searchBar.apply {
        isSubmitButtonEnabled = false
        setOnQueryTextListener(this@setUI)
    }
}

fun MainActivity.updateUI(note: List<Note>?) {
    if (note != null) {
        if (note.isNotEmpty()) {
            binding.cardView.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        } else {
            binding.cardView.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        }
    }
}

fun MainActivity.setClick() {
    binding.fabAddNote.setOnClickListener {
        startActivity(Intent(this, NewNoteActivity::class.java))
    }

    binding.fabDeleteAllNote.setOnClickListener {
        //deleteAllNotes()
        deleteNotesSelected()
    }

    binding.tickAllNote.setOnCheckedChangeListener { _, isChecked ->
        noteAdapter.selectAll(isChecked)
    }

}

fun MainActivity.deleteAllNotes() {
    val builder = AlertDialog.Builder(this)
    builder.setTitle("Delete All Notes")
    builder.setMessage("Are you sure you want to delete all notes?")
    builder.setPositiveButton("Yes") { _, _ ->
        noteViewModel.deleteAllNotes()
        Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show()
    }
    builder.setNegativeButton("No") { dialog, _ ->
        dialog.dismiss()
    }
    builder.show()
}

fun MainActivity.deleteNotesSelected() {
    val selectedNote = noteAdapter.getAllNotesSelected()

    if(selectedNote.isEmpty()) {
        Toast.makeText(this, "No notes selected", Toast.LENGTH_SHORT).show()
    }

    val builder = AlertDialog.Builder(this)
    builder.setTitle("Delete Selected Notes")
    builder.setMessage("Are you sure you want to delete the selected notes?")
    builder.setPositiveButton("Yes") { _, _ ->
        noteViewModel.deleteNotesSelected(selectedNote)
        Toast.makeText(this, "Selected note deleted successfully", Toast.LENGTH_SHORT).show()
        binding.tickAllNote.isChecked = false
    }
    builder.setNegativeButton("No") { dialog, _ ->
        dialog.dismiss()
    }
    builder.show()
}
