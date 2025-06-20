package com.example.booklibrary.ui.view.extensions

import android.content.Intent
import com.example.booklibrary.ads.app_open.AdmobAppOpenListener
import com.example.booklibrary.data.constants.ADS_OPEN_APP_UNIT_ID_SPLASH
import com.example.booklibrary.ui.view.screens.MainActivity
import com.example.booklibrary.ui.view.screens.SplashActivity

fun SplashActivity.loadAppOpenAdSplash() {
    admobAppOpen.listener = object : AdmobAppOpenListener {
        override fun onAdLoaded() {}
        override fun onFailedAdLoad(errorMessage: String) {}
        override fun onAdDismiss() {}

    }
    admobAppOpen.setAdUnitId(ADS_OPEN_APP_UNIT_ID_SPLASH)
    admobAppOpen.loadAd()
}

fun SplashActivity.goToMainActivity() {
    val intent = Intent(this, MainActivity::class.java)
    startActivity(intent)
    finish()
    jobSplash.destroy()
}