package com.g_one_hospitalapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.g_one_hospitalapp.databinding.ActivityPatientHistoryBinding

class PatientHistoryActivity : AppCompatActivity() {
    companion object {
        const val CHAT_ID = "chat_id"
    }

    private lateinit var binding: ActivityPatientHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPatientHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}