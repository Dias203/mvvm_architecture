package com.example.booklibrary.di.module

import androidx.room.Room
import com.example.booklibrary.data.api.NoteService
import com.example.booklibrary.data.api.PhotoService
import com.example.booklibrary.data.constants.BASE_URL
import com.example.booklibrary.data.database.AppDatabase
import com.example.booklibrary.data.repository.note.NoteRepository
import com.example.booklibrary.data.repository.photo.PhotoRepository
import com.example.booklibrary.ui.screens.note.NoteActivity
import com.example.booklibrary.ui.screens.newNote.NewNoteActivity
import com.example.booklibrary.ui.screens.updateNote.UpdateNoteActivity
import com.example.booklibrary.ui.screens.note.NoteViewModel
import com.example.booklibrary.ui.screens.note.adapter.NoteAdapter
import com.example.booklibrary.ui.screens.photo.PhotoActivity
import com.example.booklibrary.ui.screens.photo.PhotoViewModel
import com.example.booklibrary.ui.screens.photo.adapter.PhotoAdapter
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(NoteService::class.java)
    }

    single {
        get<Retrofit>().create(PhotoService::class.java)
    }

    // Database - Singleton cho toàn app
    single<AppDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "app_db"
        ).fallbackToDestructiveMigration() // Cần migration cho schema changes
            .build()
    }

    // Scope cho MainActivity
    scope<NoteActivity> {
        factory { NoteRepository(get(), get()) }
        factory { NoteViewModel(get()) }
        factory { NoteAdapter() }
    }

    // Scope cho UpdateNoteActivity
    scope<UpdateNoteActivity> {
        factory { NoteRepository(get(), get()) }
        factory { NoteViewModel(get()) }
    }

    // Scope cho NewNoteActivity
    scope<NewNoteActivity> {
        factory { NoteRepository(get(), get()) }
        factory { NoteViewModel(get()) }
    }

    scope<PhotoActivity> {
        factory { PhotoRepository(get(), get()) }
        factory { PhotoViewModel(get(), get()) }
        factory { PhotoAdapter(get()) }
    }
}