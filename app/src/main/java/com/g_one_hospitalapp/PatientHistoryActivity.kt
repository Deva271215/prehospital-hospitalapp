package com.g_one_hospitalapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.g_one_hospitalapp.api.ConfigAPI
import com.g_one_hospitalapp.api.responses.MessageResponse
import com.g_one_hospitalapp.databinding.ActivityPatientHistoryBinding
import com.g_one_hospitalapp.utilities.SocketIOInstance
import com.g_one_hospitalapp.utilities.UserPreference
import com.g_one_hospitalapp.view.adapters.PatientHistoryAdapter
import kotlinx.android.synthetic.main.activity_patient_history.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PatientHistoryActivity : AppCompatActivity() {
    companion object {
        const val CHAT_ID = "chat_id"
    }

    private lateinit var preference: UserPreference
    private lateinit var binding: ActivityPatientHistoryBinding
    private lateinit var adapter: PatientHistoryAdapter
    private var socket: SocketIOInstance = SocketIOInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preference = UserPreference(applicationContext)

        binding = ActivityPatientHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Adapter
        adapter = PatientHistoryAdapter()
        rvChatField.layoutManager = LinearLayoutManager(applicationContext)
        rvChatField.adapter = adapter

        socket.connectToSocketServer()
        if (!socket.getSocket()?.connected()!!) {
            socket.getSocket()?.connect()
        }
        socket.getSocket()?.emit("join_chat", CHAT_ID)

        val chatId = intent.getStringExtra(CHAT_ID)
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
                    Toast.makeText(this@PatientHistoryActivity, t.message, Toast.LENGTH_LONG).show()
                }
        })

        listenToRecvMessageEvent()
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