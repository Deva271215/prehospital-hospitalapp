package com.g_one_hospitalapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.g_one_hospitalapp.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spListFaskes = resources.getStringArray(R.array.list_faskes)
        val adapterFaskes = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, spListFaskes)
        binding.faskesName.adapter = adapterFaskes
        binding.faskesName.onItemSelectedListener = object : AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(applicationContext, spListFaskes[position], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {}
        }

        onLoginButtonClicked()
    }

    private fun onLoginButtonClicked() {
        binding.btnToSignIn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }



}