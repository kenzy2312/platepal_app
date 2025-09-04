package com.example.recipeapp.app.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.recipeapp.R
import com.example.recipeapp.Database.AppDatabase
import com.example.recipeapp.Database.User
import com.example.recipeapp.Database.UserRepository
import com.example.recipeapp.app.MainActivity
import com.example.recipeapp.app.SessionManager
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import androidx.navigation.fragment.findNavController


class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var userRepository: UserRepository
    private lateinit var sessionManager: SessionManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userDao = AppDatabase.getDatabase(requireContext()).userDao()
        userRepository = UserRepository(userDao)
        sessionManager = SessionManager(requireContext())

        val username = view.findViewById<TextInputEditText>(R.id.inputRegisterUsername)
        val email = view.findViewById<TextInputEditText>(R.id.inputRegisterEmail)
        val password = view.findViewById<TextInputEditText>(R.id.inputRegisterPassword)
        val registerBtn = view.findViewById<Button>(R.id.btnRegister)
        val loginText = view.findViewById<TextView>(R.id.textLogin)

        registerBtn.setOnClickListener {
            val uName = username.text.toString()
            val uEmail = email.text.toString()
            val pWord = password.text.toString()

            if (uName.isNotEmpty() && uEmail.isNotEmpty() && pWord.isNotEmpty()) {
                lifecycleScope.launch {
                    val user = User(username = uName, email = uEmail, password = pWord)
                    userRepository.registerUser(user)

                    sessionManager.saveAuthToken(uName)

                    Toast.makeText(requireContext(), "Registered Successfully", Toast.LENGTH_SHORT).show()

                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
                }
            } else {
                Toast.makeText(requireContext(), "Fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        loginText.setOnClickListener {
            findNavController().navigate(R.id.action_register_to_login)
        }
    }
}
