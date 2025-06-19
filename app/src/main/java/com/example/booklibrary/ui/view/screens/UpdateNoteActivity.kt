package com.example.booklibrary.ui.view.screens

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.booklibrary.data.model.Note
import com.example.booklibrary.databinding.ActivityUpdateNoteBinding
import com.example.booklibrary.ui.viewmodel.NoteViewModel
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope

class UpdateNoteActivity : AppCompatActivity(), AndroidScopeComponent {

    private lateinit var binding: ActivityUpdateNoteBinding
    private lateinit var currentNote: Note
    override val scope: Scope by activityScope()

    private val noteViewModel: NoteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getExtra()
        setOnClick()
    }

    private fun setOnClick() {
        binding.fabDone.setOnClickListener {
            updateNote()
        }

        binding.deleteNote.setOnClickListener {
            deleteNote()
        }
    }

    private fun deleteNote() {
        AlertDialog.Builder(this).apply {
            setTitle("Delete Note")
            setMessage("You want to delete this Note?")
            setPositiveButton("Delete") { _, _ ->
                noteViewModel.deleteNote(currentNote)
                Toast.makeText(this@UpdateNoteActivity, "Note was deleted", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@UpdateNoteActivity, MainActivity::class.java))
                finish()
            }
            setNegativeButton("Cancel", null)
        }.create().show()
    }

    private fun updateNote() {
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

    private fun getExtra() {
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

    override fun onDestroy() {
        super.onDestroy()
        scope.close()
    }
}