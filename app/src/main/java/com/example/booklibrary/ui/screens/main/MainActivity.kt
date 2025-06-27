package com.example.booklibrary.ui.screens.main


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.booklibrary.databinding.ActivityMainBinding
import com.example.booklibrary.ui.screens.note.NoteActivity
import com.example.booklibrary.ui.screens.photo.PhotoActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setClicked()
    }

    private fun setClicked() {
        binding.btnGoToNote.setOnClickListener {
            startActivity(Intent(this, NoteActivity::class.java))
        }

        binding.btnGoToPhoto.setOnClickListener {
            startActivity(Intent(this, PhotoActivity::class.java))
        }
    }
}