package com.g_one_hospitalapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.g_one_hospitalapp.utilities.SocketIOInstance

class PatientHistoryActivity : AppCompatActivity() {
    companion object {
        const val CHAT_ID = "chat_id"
    }

    private var socket: SocketIOInstance = SocketIOInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_history)

        socket.connectToSocketServer()
        socket.getSocket()?.connect()
        socket.getSocket()?.emit("join_chat", CHAT_ID)
    }
}