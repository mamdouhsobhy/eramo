package com.example.eramo.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.eramo.core.presentation.base.BaseActivity
import com.example.eramo.databinding.ActivitySplashBinding
import com.example.eramo.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivitySplash : BaseActivity<ActivitySplashBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        animateUp(binding.tvAppName)
        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }, SPLASH_TIME_OUT)
    }

    companion object {
        private const val SPLASH_TIME_OUT: Long = 3000
    }

}