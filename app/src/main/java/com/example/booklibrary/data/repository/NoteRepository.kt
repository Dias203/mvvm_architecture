package com.example.booklibrary.data.repository

import android.util.Log
import com.example.booklibrary.data.database.NoteDatabase
import com.example.booklibrary.data.model.Note
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NoteRepository(private val db: NoteDatabase) : KoinComponent {
    fun insertNote(note: Note) = db.getNoteDao().insertNote(note)
    fun deleteNote(note: Note) = db.getNoteDao().deleteNote(note)

    fun deleteNotesSelected(notes: List<Note>) = db.getNoteDao().deleteNotesSelected(notes)

    fun deleteAllNotes() = db.getNoteDao().deleteAllNotes()

    fun updateNote(note: Note) {
        Log.d("NoteRepository", "Updating note: $note")
        db.getNoteDao().updateNote(note)
    }

    fun getAllNotes() = db.getNoteDao().getAllNotes()
    fun searchNote(query: String) = db.getNoteDao().searchNote(query)
}