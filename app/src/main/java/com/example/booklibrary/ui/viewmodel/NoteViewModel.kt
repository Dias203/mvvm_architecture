package com.example.booklibrary.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booklibrary.data.model.Note
import com.example.booklibrary.data.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class NoteViewModel(private val noteRepository: NoteRepository) : ViewModel(), KoinComponent {
    //private val noteRepository: NoteRepository by inject()

    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO){
            noteRepository.insertNote(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.deleteNote(note)
        }
    }

    fun deleteNotesSelected(notes: List<Note>) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.deleteNotesSelected(notes)
        }
    }

    fun deleteAllNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.deleteAllNotes()
        }
    }


    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.updateNote(note)
        }
    }

    fun getAllNotes() = noteRepository.getAllNotes()

    fun searchNote(query: String) = noteRepository.searchNote(query)
}