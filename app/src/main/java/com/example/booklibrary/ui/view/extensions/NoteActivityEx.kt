package com.example.booklibrary.ui.view.extensions

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.booklibrary.data.model.note.Note
import com.example.booklibrary.ui.view.screens.NewNoteActivity
import com.example.booklibrary.ui.view.screens.NoteActivity


fun NoteActivity.setUI() {
    binding.recyclerView.apply {
        layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        setHasFixedSize(true)
        adapter = noteAdapter
    }

    noteViewModel.getAllNotes().observe(this) { note ->
        noteAdapter.differ.submitList(note)
        updateUI(note)
    }

    noteViewModel.syncNotesFromAPI().observe(this, Observer { result ->
        result.onFailure {
            if (noteAdapter.differ.currentList.isEmpty()) {
                Toast.makeText(this, "Failed to load photos and no cached data available!", Toast.LENGTH_LONG).show()
                binding.recyclerView.visibility = View.GONE
            } else {
                Toast.makeText(this, "Failed to load photos, showing cached data.", Toast.LENGTH_SHORT).show()
            }
        }
    })

    binding.searchBar.apply {
        isSubmitButtonEnabled = false
        setOnQueryTextListener(this@setUI)
    }
}

fun NoteActivity.updateUI(note: List<Note>?) {
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

fun NoteActivity.setClick() {
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

fun NoteActivity.deleteAllNotes() {
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

fun NoteActivity.deleteNotesSelected() {
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
