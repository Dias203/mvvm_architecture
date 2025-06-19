package com.example.booklibrary.ui.view.screens

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.booklibrary.data.model.Note
import com.example.booklibrary.databinding.ActivityUpdateNoteBinding
import com.example.booklibrary.ui.view.extensions.getExtra
import com.example.booklibrary.ui.view.extensions.setOnClick
import com.example.booklibrary.ui.viewmodel.NoteViewModel
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
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.close()
    }
}