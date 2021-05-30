package com.g_one_hospitalapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.g_one_hospitalapp.api.ConfigAPI
import com.g_one_hospitalapp.api.responses.ChatResponse
import com.g_one_hospitalapp.databinding.ActivityPatientHistoryBinding
import com.g_one_hospitalapp.utilities.UserPreference
import com.g_one_hospitalapp.view.adapters.PatientHistoryAdapter
import kotlinx.android.synthetic.main.activity_patient_history.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PatientHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPatientHistoryBinding
    private lateinit var adapter: PatientHistoryAdapter
    private lateinit var preference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preference = UserPreference(applicationContext)
        binding = ActivityPatientHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Riwayat Pertolongan"

        // Adapter
        adapter = PatientHistoryAdapter()
        rvHistoryList.layoutManager = LinearLayoutManager(applicationContext)
        rvHistoryList.adapter = adapter

        loadChatsHistory()
    }

    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        return true
    }

    private fun loadChatsHistory() {
        val token = preference.getLoginData().accessToken
        val hospitalId = preference.getLoginData().user?.hospital?.id
        ConfigAPI.instance.getChatsByHospital(hospitalId!!, "Bearer $token").enqueue(object: Callback<ArrayList<ChatResponse>> {
            override fun onResponse(call: Call<ArrayList<ChatResponse>>, response: Response<ArrayList<ChatResponse>>) {
                if (response.isSuccessful) {
                    adapter.setChats(response.body()!!)
                } else {
                    Toast.makeText(this@PatientHistoryActivity, "Failed to load chats", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<ChatResponse>>, t: Throwable) {
                Toast.makeText(this@PatientHistoryActivity, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}