package com.g_one_hospitalapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.g_one_hospitalapp.databinding.ActivityMainBinding
import com.g_one_hospitalapp.utilities.UserPreference
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    private lateinit var preference: UserPreference
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preference = UserPreference(applicationContext)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set hospital name
        binding.titleUName.text = preference.getLoginData().user?.hospital?.name ?: "Rumah Sakit Gadungan"

        onOpenChatButtonClicked()
        subscribeToFirebaseTopic()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.icon_history -> {
                val intent = Intent(this, PatientHistoryActivity::class.java)
                startActivity(intent)
            }
            R.id.icon_logout -> {
                preference.clearLoginData()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onOpenChatButtonClicked() {
        val chatRoomId = preference.getChatRoomId()
        if (chatRoomId.isNullOrEmpty()) {
            binding.openChatButton.visibility = View.GONE
        }
        binding.openChatButton.setOnClickListener {
            val intent = Intent(this, MedRecordActivity::class.java)
            intent.putExtra(MedRecordActivity.CHAT_ROOM_ID, chatRoomId)
            startActivity(intent)
        }
    }

    private fun subscribeToFirebaseTopic() {
        val hospitalCode = preference.getLoginData().user?.hospital?.code
        FirebaseMessaging.getInstance().subscribeToTopic(hospitalCode.toString()).addOnCompleteListener {
            var message = "Subscribe to $hospitalCode successful!"
            if (!it.isSuccessful) {
                message = "Failed to subscribe to $hospitalCode!"
            }
            Log.i("hospital_code", hospitalCode!!)
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
        }
    }
}