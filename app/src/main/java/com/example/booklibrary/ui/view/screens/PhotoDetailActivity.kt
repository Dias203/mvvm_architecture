package com.example.booklibrary.ui.view.screens

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.booklibrary.R
import com.example.booklibrary.data.model.photo.PhotoItem
import com.example.booklibrary.databinding.ActivityPhotoDetailBinding
import com.example.booklibrary.ui.view.extensions.getIntentExtra
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.core.scope.Scope

class PhotoDetailActivity : AppCompatActivity(), AndroidScopeComponent {
    lateinit var binding: ActivityPhotoDetailBinding
    lateinit var currentPhoto: PhotoItem
    override val scope: Scope by activityScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getIntentExtra()
    }


    override fun onDestroy() {
        super.onDestroy()
        scope.close()
    }
}