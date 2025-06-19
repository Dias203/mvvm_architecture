package com.example.booklibrary.ui.view.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.booklibrary.databinding.ActivityNewNoteBinding
import com.example.booklibrary.data.model.Note
import com.example.booklibrary.ui.viewmodel.NoteViewModel
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope

class NewNoteActivity : AppCompatActivity(), AndroidScopeComponent {

    private lateinit var binding: ActivityNewNoteBinding

    override val scope: Scope by activityScope()
    private val noteViewModel: NoteViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClick()
    }

    private fun setOnClick() {
        binding.saveNote.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        val noteTitle = binding.edtNoteTitle.text.toString().trim()
        val noteBody = binding.edtNoteBody.text.toString().trim()

        if(noteTitle.isNotEmpty()) {
            val note = Note(0, noteTitle, noteBody)
            noteViewModel.addNote(note)
            Toast.makeText(this, "Note Saved Successfully", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, MainActivity::class.java))
        }
        else {
            Toast.makeText(this, "Please Enter Note Title", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.close()
    }
}