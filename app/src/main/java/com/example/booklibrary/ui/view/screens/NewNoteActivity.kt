package com.example.booklibrary.ui.view.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.booklibrary.databinding.ActivityNewNoteBinding
import com.example.booklibrary.data.model.Note
import com.example.booklibrary.ui.view.extensions.saveNote
import com.example.booklibrary.ui.view.extensions.setClick
import com.example.booklibrary.ui.viewmodel.NoteViewModel
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.androidx.viewmodel.ext.android.viewModel
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
    }


    override fun onDestroy() {
        super.onDestroy()
        scope.close()
    }
}