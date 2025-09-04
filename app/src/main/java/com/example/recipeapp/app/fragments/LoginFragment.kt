package com.example.recipeapp.app.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.R
import com.example.recipeapp.app.SessionManager
import com.example.recipeapp.Database.AppDatabase
import com.example.recipeapp.Database.UserRepository
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var sessionManager: SessionManager
    private lateinit var userRepository: UserRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())
        val userDao = AppDatabase.getDatabase(requireContext()).userDao()
        userRepository = UserRepository(userDao)

        val username = view.findViewById<TextInputEditText>(R.id.inputUsername)
        val password = view.findViewById<TextInputEditText>(R.id.inputPassword)
        val loginBtn = view.findViewById<Button>(R.id.btnLogin)
        val registerText = view.findViewById<TextView>(R.id.textRegister)

        loginBtn.setOnClickListener {
            lifecycleScope.launch {
                val user = userRepository.loginUser(
                    username.text.toString(),
                    password.text.toString()
                )

                if (user != null) {
                    sessionManager.saveAuthToken(user.username)
                    Toast.makeText(requireContext(), "Login Successful", Toast.LENGTH_SHORT).show()

                    requireActivity().runOnUiThread {
                        startActivity(android.content.Intent(requireContext(), com.example.recipeapp.app.MainActivity::class.java))
                        requireActivity().finish()
                    }
                } else {
                    Toast.makeText(requireContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show()
                }
            }
        }

        registerText.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_register)
        }
    }
}