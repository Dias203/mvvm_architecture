package com.example.booklibrary.ads.app_open

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.booklibrary.data.constants.ADS_OPEN_APP_UNIT_ID_DEFAULT
import com.example.booklibrary.ui.view.screens.SplashActivity
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdmobAppOpenApplication(
    private val application: Application, private val context: Context
) : DefaultLifecycleObserver {

    private val admobAppOpen by lazy {
        AdmobAppOpen(context).apply {
            setAdUnitId(ADS_OPEN_APP_UNIT_ID_DEFAULT)
            listener = object : AdmobAppOpenListener {
                override fun onAdLoaded() {}
                override fun onFailedAdLoad(errorMessage: String) {}
                override fun onAdDismiss() { loadAd() }
            }
        }
    }

    private var currentActivity: Activity? = null
    private var isLocked = false
    private var isMobileAdsInitialized = false



    fun initialize() {
        val backgroundScope = CoroutineScope(Dispatchers.IO)
        backgroundScope.launch {
            MobileAds.initialize(application) {
                isMobileAdsInitialized = true
                loadAd()
            }
        }

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    fun locked() {
        isLocked = true
    }

    fun unlock() {
        isLocked = false
    }


    fun onActivityStarted(activity: Activity) {
        currentActivity = activity
    }

    fun onActivityDestroyed(activity: Activity) {
        if (currentActivity == activity) {
            currentActivity = null
        }
    }

    override fun onStart(owner: LifecycleOwner) {
        if (currentActivity is SplashActivity) return
        if (admobAppOpen.isShowing()) return
        if (isLocked) return

        Log.i("DUC", "onStart: $currentActivity")

        showAd(currentActivity!!)
    }

    override fun onStop(owner: LifecycleOwner) {}

    private fun loadAd() {
        admobAppOpen.loadAd()
    }

    private fun showAd(activity: Activity) {
        if (isLocked) return
        admobAppOpen.attachOverlayToActivity(activity)
        admobAppOpen.showAd(activity) {}
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        admobAppOpen.destroyAd()
    }
}