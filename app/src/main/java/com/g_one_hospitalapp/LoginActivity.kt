package com.g_one_hospitalapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.g_one_hospitalapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBtnToSignUpClicked()
        onLoginButtonClicked()
    }

    private fun onBtnToSignUpClicked() {
        binding.btnToSignUp.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onLoginButtonClicked(){
       binding.login.setOnClickListener{
           val intent = Intent(this@LoginActivity, MainActivity::class.java)
           startActivity(intent)
       }
    }
}