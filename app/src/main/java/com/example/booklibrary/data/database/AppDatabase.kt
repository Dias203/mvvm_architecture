package com.example.booklibrary.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.booklibrary.data.model.note.Note
import com.example.booklibrary.data.model.photo.PhotoItem

@Database(entities = [Note::class, PhotoItem::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDAO
    abstract fun getPhotoDao(): PhotoDAO

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()
        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS photos (
                        id INTEGER PRIMARY KEY NOT NULL,
                        albumId INTEGER NOT NULL,
                        title TEXT NOT NULL,
                        url TEXT NOT NULL,
                        thumbnailUrl TEXT NOT NULL
                    )
                    """
                )
            }
        }

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_db"
        ).addMigrations(MIGRATION_3_4)
            .build()
    }
}