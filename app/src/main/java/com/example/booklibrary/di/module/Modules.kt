package com.example.booklibrary.di.module

import androidx.room.Room
import com.example.booklibrary.data.database.NoteDatabase
import com.example.booklibrary.data.model.NoteInstance
import com.example.booklibrary.data.repository.NoteRepository
import com.example.booklibrary.ui.view.screens.MainActivity
import com.example.booklibrary.ui.view.screens.NewNoteActivity
import com.example.booklibrary.ui.view.screens.UpdateNoteActivity
import com.example.booklibrary.ui.viewmodel.NoteViewModel
import com.example.notetakingapp.adapter.NoteAdapter
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {

    // Database - Singleton cho toàn app
    single<NoteDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            NoteDatabase::class.java,
            "note_db"
        ).fallbackToDestructiveMigration() // Cần migration cho schema changes
            .build()
    }

    // API Service - Singleton
    single { NoteInstance.noteService }

    // Scope cho MainActivity
    scope<MainActivity> {
        factory { NoteRepository(get()) }
        factory { NoteViewModel(get()) }
        factory { NoteAdapter() }
    }

    // Scope cho UpdateNoteActivity
    scope<UpdateNoteActivity> {
        factory { NoteRepository(get()) }
        factory { NoteViewModel(get()) }
    }

    // Scope cho NewNoteActivity
    scope<NewNoteActivity> {
        factory { NoteRepository(get()) }
        factory { NoteViewModel(get()) }
    }
}