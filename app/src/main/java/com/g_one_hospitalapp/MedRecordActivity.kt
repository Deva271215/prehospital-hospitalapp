package com.g_one_hospitalapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.g_one_hospitalapp.api.ConfigAPI
import com.g_one_hospitalapp.api.responses.MessageResponse
import com.g_one_hospitalapp.databinding.ActivityMedRecordBinding
import com.g_one_hospitalapp.utilities.SocketIOInstance
import com.g_one_hospitalapp.utilities.UserPreference
import com.g_one_hospitalapp.view.adapters.MedRecordAdapter
import kotlinx.android.synthetic.main.activity_med_record.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MedRecordActivity : AppCompatActivity() {
    companion object {
        const val CHAT_ID = "chat_id"
        const val IS_FROM_HISTORY = "is_from_history"
    }

    private lateinit var preference: UserPreference
    private lateinit var adapter: MedRecordAdapter
    private lateinit var binding: ActivityMedRecordBinding
    private var socket: SocketIOInstance = SocketIOInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preference = UserPreference(applicationContext)
        binding = ActivityMedRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Rekam Medis"

        adapter = MedRecordAdapter()
        rvChatField.layoutManager = LinearLayoutManager(applicationContext)
        rvChatField.adapter = adapter

        val isFromHistory = intent.getBooleanExtra(IS_FROM_HISTORY, false)
        if (!isFromHistory) {
            socket.connectToSocketServer()
            if (!socket.getSocket()?.connected()!!) {
                socket.getSocket()?.connect()
            }
            socket.getSocket()?.emit("join_chat", PatientHistoryActivity.CHAT_ID)
        }

        val chatId = intent.getStringExtra(PatientHistoryActivity.CHAT_ID)
        ConfigAPI.instance
            .getMessages(chatId!!, "Bearer ${preference.getLoginData().accessToken}")
            .enqueue(object: Callback<ArrayList<MessageResponse>> {
                override fun onResponse(
                    call: Call<ArrayList<MessageResponse>>,
                    response: Response<ArrayList<MessageResponse>>
                ) {
                    if (response.isSuccessful) {
                        adapter.setMessages(response.body()!!)
                    }
                }

                override fun onFailure(call: Call<ArrayList<MessageResponse>>, t: Throwable) {
                    Toast.makeText(this@MedRecordActivity, t.message, Toast.LENGTH_LONG).show()
                }
            })

        listenToRecvMessageEvent()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun listenToRecvMessageEvent() {
        socket.getSocket()?.on("recv_message") {
            adapter.setMessage(it[0] as MessageResponse)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        socket.getSocket()?.disconnect()
    }
}