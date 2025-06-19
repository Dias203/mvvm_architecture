package com.example.booklibrary.ui.view.extensions

import android.content.Intent
import android.view.View
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
}