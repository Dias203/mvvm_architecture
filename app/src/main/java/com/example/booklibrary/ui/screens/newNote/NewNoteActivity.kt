package com.example.booklibrary.ui.screens.newNote

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.booklibrary.databinding.ActivityNewNoteBinding
import com.example.booklibrary.ui.screens.note.NoteViewModel
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.core.scope.Scope

class NewNoteActivity : AppCompatActivity(), AndroidScopeComponent {

    lateinit var binding: ActivityNewNoteBinding

    override val scope: Scope by activityScope()
    val noteViewModel: NoteViewModel by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setClick()
        noteViewModel.syncNotes()
    }


    override fun onDestroy() {
        super.onDestroy()
        scope.close()
    }
}