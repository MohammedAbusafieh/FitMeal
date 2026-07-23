package com.example.fitmeal

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fitmeal.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val name = binding.edtName.text.toString().trim()
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                binding.txtError.text = "All fields are required."
                binding.txtError.visibility = android.view.View.VISIBLE
                return@setOnClickListener
            }

            // Save to SharedPreferences
            val prefs = getSharedPreferences("FitMealUsers", MODE_PRIVATE)
            prefs.edit().apply {
                putString("email", email)
                putString("password", password)
                putString("name", name)
                apply()
            }

            Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show()

            // Go back to login
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
