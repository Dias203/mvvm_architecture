package com.example.booklibrary.ads.app_open

interface AdmobAppOpenListener {
    fun onAdLoaded()
    fun onFailedAdLoad(errorMessage: String)
    fun onAdDismiss()
}