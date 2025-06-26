package com.example.booklibrary.ui.view.extensions

import android.content.Intent
import android.widget.Toast
import com.example.booklibrary.data.model.note.Note
import com.example.booklibrary.ui.view.screens.NoteActivity
import com.example.booklibrary.ui.view.screens.NewNoteActivity

fun NewNoteActivity.setClick() {
    binding.saveNote.setOnClickListener {
        saveNote()
    }
}

fun NewNoteActivity.saveNote() {
    val noteTitle = binding.edtNoteTitle.text.toString().trim()
    val noteBody = binding.edtNoteBody.text.toString().trim()

    if(noteTitle.isNotEmpty()) {
        val note = Note(0, noteTitle, noteBody)
        noteViewModel.addNote(note)
        Toast.makeText(this, "Note Saved Successfully", Toast.LENGTH_LONG).show()
        startActivity(Intent(this, NoteActivity::class.java))
    }
    else {
        Toast.makeText(this, "Please Enter Note Title", Toast.LENGTH_LONG).show()
    }
}