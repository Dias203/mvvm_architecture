package com.example.booklibrary.ui.view.screens

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.booklibrary.R
import com.example.booklibrary.data.model.photo.PhotoItem
import com.example.booklibrary.databinding.ActivityPhotoBinding
import com.example.booklibrary.ui.adapter.PhotoAdapter
import com.example.booklibrary.ui.view.extensions.setupUI
import com.example.booklibrary.ui.viewmodel.PhotoViewModel
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope

class PhotoActivity : AppCompatActivity(), AndroidScopeComponent {
    lateinit var binding: ActivityPhotoBinding
    override val scope: Scope by activityScope()
    val photoAdapter: PhotoAdapter by inject()
    val photoViewModel: PhotoViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.close()
    }
}