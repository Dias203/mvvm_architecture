package com.example.booklibrary.ui.view.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.booklibrary.databinding.ActivityNoteBinding
import com.example.booklibrary.ui.view.extensions.setClick
import com.example.booklibrary.ui.view.extensions.setUI
import com.example.booklibrary.ui.viewmodel.NoteViewModel
import com.example.booklibrary.ui.adapter.NoteAdapter
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope

class NoteActivity : AppCompatActivity(), SearchView.OnQueryTextListener, AndroidScopeComponent {

    lateinit var binding: ActivityNoteBinding
    override val scope: Scope by activityScope()

    val noteViewModel: NoteViewModel by viewModel()
    val noteAdapter: NoteAdapter by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUI()
        setClick()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        searchNote(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchNote(newText)
        }
        return true
    }

    private fun searchNote(query: String?) {
        val searchQuery = "%$query%"
        noteViewModel.searchNote(searchQuery).observe(this) { list ->
            noteAdapter.differ.submitList(list)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.close()
    }
}