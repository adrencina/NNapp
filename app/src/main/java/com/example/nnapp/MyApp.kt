package com.example.nnapp

import android.app.Application
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseFirestore.setLoggingEnabled(true) // Habilita logs de Firestore
    }
}