package com.example.booklibrary.ui.screens.note

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.booklibrary.databinding.ActivityNoteBinding
import com.example.booklibrary.ui.screens.note.adapter.NoteAdapter
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope

class NoteActivity : AppCompatActivity(), SearchView.OnQueryTextListener, AndroidScopeComponent {

    val binding: ActivityNoteBinding by lazy {
        ActivityNoteBinding.inflate(layoutInflater)
    }
    override val scope: Scope by activityScope()
    val noteViewModel: NoteViewModel by viewModel()
    val noteAdapter: NoteAdapter by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupRecyclerView()
        registerObserver()
        setUI()
        setupClicks()

        noteViewModel.syncNotes()
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
        noteViewModel.searchNote(searchQuery)
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.close()
    }
}