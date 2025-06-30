package com.example.booklibrary.ui.screens.photo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.booklibrary.databinding.ActivityPhotoBinding
import com.example.booklibrary.ui.screens.photo.adapter.PhotoAdapter
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

        registerListener()
        setupRecyclerView()
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.close()
    }
}