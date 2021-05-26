package com.g_one_hospitalapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.g_one_hospitalapp.api.ConfigAPI
import com.g_one_hospitalapp.api.responses.LoginResponse
import com.g_one_hospitalapp.databinding.ActivityLoginBinding
import com.g_one_hospitalapp.models.UserEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
           // Get input values
           val emailValue = binding.groupEmail.text.toString()
           val passwordValue = binding.groupPass.text.toString()

           when {
               emailValue.isEmpty() -> {
                   Toast.makeText(this@LoginActivity, "Email tidak boleh kosong", Toast.LENGTH_LONG).show()
               }
               passwordValue.isEmpty() -> {
                   Toast.makeText(this@LoginActivity, "Password tidak boleh kosong", Toast.LENGTH_LONG).show()
               }
               else -> {
                   val user = UserEntity(email = emailValue, password = passwordValue)

                   ConfigAPI.instance.signInUser(user).enqueue(object: Callback<LoginResponse> {
                       override fun onResponse(
                           call: Call<LoginResponse>,
                           response: Response<LoginResponse>
                       ) {
                           if (response.isSuccessful) {
                               val intent = Intent(this@LoginActivity, MainActivity::class.java)
                               startActivity(intent)
                           } else {
                               Toast.makeText(applicationContext, "Gagal login.", Toast.LENGTH_LONG).show()
                           }
                       }

                       override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                           Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                       }
                   })
               }
           }
       }
    }
}