package com.example.booklibrary.ui.screens.photoDetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.booklibrary.data.model.photo.PhotoItem
import com.example.booklibrary.databinding.ActivityPhotoDetailBinding
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