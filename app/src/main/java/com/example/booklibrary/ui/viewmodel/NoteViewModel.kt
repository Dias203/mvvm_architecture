package com.example.booklibrary.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booklibrary.data.model.note.Note
import com.example.booklibrary.data.repository.NoteRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class NoteViewModel(private val noteRepository: NoteRepository) : ViewModel(), KoinComponent {
    private val _syncResult = MutableLiveData<Result<Unit>>()
    fun syncNotesFromAPI(): LiveData<Result<Unit>> = _syncResult

    init {
        syncNotes()
    }

    private fun syncNotes() {
        viewModelScope.launch {
            //noteRepository.syncNotesFromApi()
            _syncResult.postValue(noteRepository.syncNotesFromApi())
        }
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            val result = noteRepository.createNoteWithApi(note)
            result.onFailure {
                // Handle error
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            val result = noteRepository.deleteNoteWithApi(note)
            result.onFailure {
                // Handle error
            }
        }
    }

    fun deleteNotesSelected(notes: List<Note>) {
        viewModelScope.launch {
            notes.forEach { note ->
                deleteNote(note)
            }
        }
    }

    fun deleteAllNotes() {
        viewModelScope.launch {
            noteRepository.deleteAllNotes()
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            val result = noteRepository.updateNoteWithApi(note)
            result.onFailure {
                // Handle error
            }
        }
    }

    fun getAllNotes() = noteRepository.getAllNotes()

    fun searchNote(query: String) = noteRepository.searchNote(query)

    fun refreshNotes() {
        syncNotes()
    }
}
