package com.example.booklibrary.ui.view.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.booklibrary.R
import com.example.booklibrary.databinding.ActivityMainBinding

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