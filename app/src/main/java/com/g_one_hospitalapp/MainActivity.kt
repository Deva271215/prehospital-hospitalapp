package com.g_one_hospitalapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.g_one_hospitalapp.utilities.UserPreference
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    private lateinit var preference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preference = UserPreference(applicationContext)

        val data = preference.getLoginData()
        FirebaseMessaging.getInstance().subscribeToTopic(data.user?.hospital?.code.toString())
    }
}