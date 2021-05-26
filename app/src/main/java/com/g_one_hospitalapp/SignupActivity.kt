package com.g_one_hospitalapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.g_one_hospitalapp.api.ConfigAPI
import com.g_one_hospitalapp.api.responses.HospitalsResponse
import com.g_one_hospitalapp.api.responses.SignUpResponse
import com.g_one_hospitalapp.databinding.ActivitySignupBinding
import com.g_one_hospitalapp.models.UserEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var hospitals: List<HospitalsResponse>

    private var hospitalOptions = ArrayList<String>()
    private lateinit var selectedHospital: HospitalsResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        callHospitalFromRemote()
        onLoginButtonClicked()
        onSignUpButtonClicked()
    }

    private fun onSignUpButtonClicked() {
        binding.signUpButton.setOnClickListener {
            // Get input values
            val emailValue = binding.groupEmail.text.toString()
            val passwordValue = binding.groupPass.text.toString()
            val telpValue = binding.groupNumber.text.toString()
            val accountTypeValue = "DOCTOR"

            // Validate input values
            when {
                emailValue.isEmpty() -> {
                    Toast.makeText(this@SignupActivity, "Email tidak boleh kosong", Toast.LENGTH_LONG).show()
                }
                passwordValue.isEmpty() -> {
                    Toast.makeText(this@SignupActivity, "Password tidak boleh kosong", Toast.LENGTH_LONG).show()
                }
                telpValue.isEmpty() -> {
                    Toast.makeText(this@SignupActivity, "Nomor telepon tidak boleh kosong", Toast.LENGTH_LONG).show()
                }
                else -> {
                    // Create new user
                    val user = UserEntity(
                            email = emailValue,
                            password = passwordValue,
                            noHp = telpValue,
                            accountType = accountTypeValue,
                            hospital = selectedHospital
                    )

                    // Call API
                    ConfigAPI.instance.createUser(user).enqueue(object: Callback<SignUpResponse> {
                        override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                            if (response.isSuccessful) {
                                val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                                startActivity(intent)

                                Toast.makeText(this@SignupActivity, response.body()?.message, Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(this@SignupActivity, "Proses gagal dalam membuat akun", Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                            Toast.makeText(this@SignupActivity, t.message, Toast.LENGTH_LONG).show()
                        }
                    })
                }
            }
        }
    }

    private fun onLoginButtonClicked() {
        binding.btnToSignIn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupHospitalSpinner() {
        val adapterFaskes = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, hospitalOptions)
        binding.faskesName.adapter = adapterFaskes
        binding.faskesName.onItemSelectedListener = object : AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedHospital = hospitals[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {}
        }
    }

    private fun callHospitalFromRemote() {
        ConfigAPI.instance.getHospitals().enqueue(object: Callback<List<HospitalsResponse>> {
            override fun onResponse(call: Call<List<HospitalsResponse>>, response: Response<List<HospitalsResponse>>) {
                val data = response.body()
                if (response.isSuccessful) {
                    hospitals = data!!
                    for (item in data) {
                        hospitalOptions.add(item.name)
                    }
                    setupHospitalSpinner()
                } else {
                    Toast.makeText(this@SignupActivity, "Rumah sakit tidak berhasil didapat.", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<HospitalsResponse>>, t: Throwable) {
                Toast.makeText(this@SignupActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

}