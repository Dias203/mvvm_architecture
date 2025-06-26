package com.example.booklibrary.di.module

import androidx.room.Room
import com.example.booklibrary.data.database.AppDatabase
import com.example.booklibrary.data.model.note.AppInstance
import com.example.booklibrary.data.model.photo.PhotoItem
import com.example.booklibrary.data.repository.NoteRepository
import com.example.booklibrary.data.repository.PhotoRepository
import com.example.booklibrary.ui.view.screens.NoteActivity
import com.example.booklibrary.ui.view.screens.NewNoteActivity
import com.example.booklibrary.ui.view.screens.UpdateNoteActivity
import com.example.booklibrary.ui.viewmodel.NoteViewModel
import com.example.booklibrary.ui.adapter.NoteAdapter
import com.example.booklibrary.ui.adapter.PhotoAdapter
import com.example.booklibrary.ui.view.screens.PhotoActivity
import com.example.booklibrary.ui.view.screens.PhotoDetailActivity
import com.example.booklibrary.ui.viewmodel.PhotoViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {

    // Database - Singleton cho toàn app
    single<AppDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "app_db"
        ).fallbackToDestructiveMigration() // Cần migration cho schema changes
            .build()
    }

    // API Service - Singleton
    single { AppInstance.noteService }

    // Scope cho MainActivity
    scope<NoteActivity> {
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

    scope<PhotoActivity> {
        factory { PhotoRepository(get()) }
        factory { PhotoViewModel(get()) }
        factory { PhotoAdapter(get()) }
    }
}