package com.example.booklibrary.di.module

import com.example.booklibrary.application.MyApplication
import com.example.booklibrary.utils.JobSplash
import com.example.booklibrary.ads.app_open.AdmobAppOpen
import com.example.booklibrary.ads.app_open.AdmobAppOpenApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val module2 = module {

    single { AdmobAppOpenApplication(androidContext() as MyApplication, get()) }

    scope<SplashActivity> {
        factory { JobSplash() }
        factory { AdmobAppOpen(get()) }
    }
}