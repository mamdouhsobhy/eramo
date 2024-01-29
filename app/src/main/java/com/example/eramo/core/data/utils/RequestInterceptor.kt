package com.example.eramo.core.data.utils

import com.example.eramo.core.presentation.common.SharedPrefs
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor constructor(private val pref: SharedPrefs) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val authToken = "Bearer " + pref.getToken()
        //val firebaseToken = pref.getFirebaseToken()
        val newRequest = chain.request().newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json")
            .addHeader("lang", pref.getPreferredLocale())
            .build()
        return chain.proceed(newRequest)
    }
}