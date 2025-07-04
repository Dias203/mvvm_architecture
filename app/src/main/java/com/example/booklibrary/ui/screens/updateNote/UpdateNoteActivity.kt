package com.example.booklibrary.ui.screens.updateNote

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.booklibrary.data.model.note.Note
import com.example.booklibrary.databinding.ActivityUpdateNoteBinding
import com.example.booklibrary.ui.screens.note.NoteViewModel
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope

class UpdateNoteActivity : AppCompatActivity(), AndroidScopeComponent {

    lateinit var binding: ActivityUpdateNoteBinding
    lateinit var currentNote: Note
    override val scope: Scope by activityScope()
    val noteViewModel: NoteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getExtra()
        setOnClick()
        noteViewModel.syncNotes()
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.close()
    }
}