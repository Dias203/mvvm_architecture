package com.example.booklibrary.application

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.booklibrary.di.module.appModule
import com.example.booklibrary.di.module.module2
import com.example.booklibrary.ads.app_open.AdmobAppOpenApplication
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application(), Application.ActivityLifecycleCallbacks{

    private val admobAppOpenManager: AdmobAppOpenApplication by inject()

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(this)
        startKoin {
            androidContext(this@MyApplication)
            modules(listOf(appModule, module2))
        }
        admobAppOpenManager.initialize()
        ProcessLifecycleOwner.get().lifecycle.addObserver(admobAppOpenManager)
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {}

    override fun onActivityStarted(p0: Activity) {
        admobAppOpenManager.onActivityStarted(activity = p0)
    }
    override fun onActivityResumed(p0: Activity) {}
    override fun onActivityPaused(p0: Activity) {}
    override fun onActivityStopped(p0: Activity) {}

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {}
    override fun onActivityDestroyed(p0: Activity) {
        admobAppOpenManager.onActivityDestroyed(activity = p0)
    }
}