package com.example.fitmeal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val edtName = view.findViewById<EditText>(R.id.edtName)
        val edtEmail = view.findViewById<EditText>(R.id.edtEmail)
        val edtPassword = view.findViewById<EditText>(R.id.edtPassword)
        val txtError = view.findViewById<TextView>(R.id.txtError)
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)
        val txtRegister = view.findViewById<TextView>(R.id.txtRegister)

        btnLogin.setOnClickListener {
            val name = edtName.text.toString().trim()
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                txtError.visibility = View.VISIBLE
                txtError.text = "Please fill all fields."
            } else {
                // ✅ Validate with SharedPreferences
                val prefs = requireContext().getSharedPreferences("FitMealUsers", 0)
                val savedName = prefs.getString("name", "")
                val savedEmail = prefs.getString("email", "")
                val savedPassword = prefs.getString("password", "")

                if (name != savedName || email != savedEmail || password != savedPassword) {
                    txtError.visibility = View.VISIBLE
                    txtError.text = "Incorrect name, email, or password."
                } else {
                    txtError.visibility = View.GONE
                    // Go to HomeActivity
                    val intent = Intent(requireActivity(), HomeActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
        }

        // Open RegisterActivity
        txtRegister.setOnClickListener {
            val intent = Intent(requireActivity(), RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
