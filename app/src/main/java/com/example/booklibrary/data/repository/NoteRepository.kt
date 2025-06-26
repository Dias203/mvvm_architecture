package com.example.booklibrary.data.repository

import android.util.Log
import com.example.booklibrary.data.database.AppDatabase
import com.example.booklibrary.data.model.note.Note
import com.example.booklibrary.data.model.note.AppInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class NoteRepository(private val db: AppDatabase) : KoinComponent {

    private val noteService = AppInstance.noteService

    suspend fun insertNote(note: Note) = withContext(Dispatchers.IO) {
        db.getNoteDao().insertNote(note)
    }

    suspend fun deleteNote(note: Note) = withContext(Dispatchers.IO) {
        db.getNoteDao().deleteNote(note)
    }

    suspend fun deleteNotesSelected(notes: List<Note>) = withContext(Dispatchers.IO) {
        db.getNoteDao().deleteNotesSelected(notes)
    }

    suspend fun deleteAllNotes() = withContext(Dispatchers.IO) {
        db.getNoteDao().deleteAllNotes()
    }

    suspend fun updateNote(note: Note) = withContext(Dispatchers.IO) {
        Log.d("DUC", "Updating note: $note")
        db.getNoteDao().updateNote(note)
    }

    fun getAllNotes() = db.getNoteDao().getAllNotes()
    fun searchNote(query: String) = db.getNoteDao().searchNote(query)

    // Xử lý đồng bộ dữ liệu từ API và ROOM
    suspend fun syncNotesFromApi(): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val localCount = db.getNoteDao().getNotesCount()
            if (localCount == 0) {
                Log.i("DUC", "Room is empty, fetching from API...")
                val response = noteService.getNotes()

                return@withContext if (response.isSuccessful) {
                    response.body()?.let { notes ->
                        notes.forEach { note ->
                            db.getNoteDao().insertNoteIfNotExists(note.copy(isSynced = true))
                        }
                    }
                    Result.success(Unit)
                } else {
                    Result.failure(Exception("API call failed: ${response.code()}"))
                }
            } else {
                Log.i("DUC", "Room has data, skipping API call")
                return@withContext Result.success(Unit)
            }
        } catch (e: Exception) {
            Log.e("DUC", "Error syncing notes", e)
            return@withContext Result.failure(e)
        }
    }


    suspend fun createNoteWithApi(note: Note): Result<Note> = withContext(Dispatchers.IO) {
        try {
            // Tạo note local trước (với ID tạm thời)
            val localNote = note.copy(isLocalOnly = true, isSynced = false)
            insertNote(localNote)

            // Call API để tạo note trên server
            val response = noteService.createNote(note)

            if (response.isSuccessful) {
                response.body()?.let { serverNote ->
                    // Update với ID từ server
                    val syncedNote = serverNote.copy(isSynced = true, isLocalOnly = false)
                    db.getNoteDao().updateNote(syncedNote)
                    Result.success(syncedNote)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("Failed to create note on server: ${response.code()}"))
            }
        } catch (e: Exception) {
            Log.i("DUC", "Error creating note", e)
            Result.failure(e)
        }
    }

    suspend fun updateNoteWithApi(note: Note): Result<Note> = withContext(Dispatchers.IO) {
        try {
            // Update local trước
            updateNote(note.copy(isSynced = false))

            // Call API để update trên server
            val response = noteService.updateNote(note.id, note)

            if (response.isSuccessful) {
                response.body()?.let { serverNote ->
                    val syncedNote = serverNote.copy(isSynced = true)
                    updateNote(syncedNote)
                    Result.success(syncedNote)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("Failed to update note on server: ${response.code()}"))
            }
        } catch (e: Exception) {
            Log.i("DUC", "Error updating note", e)
            Result.failure(e)
        }
    }

    suspend fun deleteNoteWithApi(note: Note): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            // Delete local trước
            deleteNote(note)

            // Call API để delete trên server (chỉ khi note đã được đồng bộ)
            if (!note.isLocalOnly) {
                val response = noteService.deleteNote(note.id)

                if (response.isSuccessful) {
                    Result.success(Unit)
                } else {
                    Log.i("DUC", "Failed to delete note on server: ${response.code()}")
                    Result.failure(Exception("Failed to delete note on server: ${response.code()}"))
                }
            } else {
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Log.i("DUC", "Error deleting note", e)
            Result.failure(e)
        }
    }
}
