package com.example.eramo

import android.app.Application
import com.example.eramo.core.presentation.common.SharedPrefs
import com.example.eramo.core.presentation.utilities.LocaleHelper
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class EramoApplication : Application() {

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    override fun onCreate() {
        super.onCreate()
        LocaleHelper.onAttach(applicationContext)
        LocaleHelper.setLocale(sharedPrefs.getPreferredLocale())
    }

}