package com.example.booklibrary.ui.screens.updateNote

import android.app.AlertDialog
import android.content.Intent
import android.widget.Toast
import com.example.booklibrary.data.model.note.Note
import com.example.booklibrary.ui.screens.note.NoteActivity


fun UpdateNoteActivity.getExtra() {
    val note = intent.getSerializableExtra("note") as? Note
    if (note != null) {
        currentNote = note
        binding.edtNoteTitleUpdate.setText(note.noteTitle)
        binding.edtNoteBodyUpdate.setText(note.noteBody)
    } else {
        Toast.makeText(this, "Error: Can't find note", Toast.LENGTH_SHORT).show()
        finish()
    }
}

fun UpdateNoteActivity.setOnClick() {
    binding.fabDone.setOnClickListener {
        updateNote()
    }

    binding.deleteNote.setOnClickListener {
        deleteNote()
    }
}

fun UpdateNoteActivity.updateNote() {
    val title = binding.edtNoteTitleUpdate.text.toString().trim()
    val body = binding.edtNoteBodyUpdate.text.toString().trim()

    if (title.isNotEmpty()) {
        val note = Note(currentNote.id, title, body)
        noteViewModel.updateNote(note)
        Toast.makeText(this, "Update Note successful", Toast.LENGTH_LONG).show()
        finish()
    }
    else {
        Toast.makeText(this, "Please enter the title", Toast.LENGTH_LONG).show()
    }
}

fun UpdateNoteActivity.deleteNote() {
    AlertDialog.Builder(this).apply {
        setTitle("Delete Note")
        setMessage("You want to delete this Note?")
        setPositiveButton("Delete") { _, _ ->
            noteViewModel.deleteNote(currentNote)
            Toast.makeText(this@deleteNote, "Note was deleted", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@deleteNote, NoteActivity::class.java))
            finish()
        }
        setNegativeButton("Cancel", null)
    }.create().show()
}