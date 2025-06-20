package com.example.booklibrary.ui.view.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.booklibrary.R
import com.example.booklibrary.ui.view.extensions.goToMainActivity
import com.example.booklibrary.ui.view.extensions.loadAppOpenAdSplash
import com.example.booklibrary.utils.JobSplash
import com.example.booklibrary.ads.app_open.AdmobAppOpen
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.core.scope.Scope

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity(), AndroidScopeComponent, JobSplash.ProgressUpdated {

    override val scope: Scope by activityScope()
    val jobSplash: JobSplash by inject()
    val admobAppOpen: AdmobAppOpen by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        loadAppOpenAdSplash()
    }

    override fun onPause() {
        super.onPause()
        jobSplash.stopJob()
    }

    override fun onResume() {
        super.onResume()
        jobSplash.startJob(this)
    }

    override fun onProgressUpdate(count: Int) {
        findViewById<ProgressBar>(R.id.loadingBar).progress = count

        if(jobSplash.isProgressMax()) {
            goToMainActivity()
            return
        }
        if (admobAppOpen.isLoaded()) {
            jobSplash.stopJob()
            admobAppOpen.showAd(this) {
                goToMainActivity()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        jobSplash.destroy()
    }

}