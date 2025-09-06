package com.example.recipeapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.R
import com.example.recipeapp.app.AuthActivity
import com.example.recipeapp.app.SessionManager

class MoreFragment : Fragment() {

    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_more, container, false)

        val btnAbout = view.findViewById<Button>(R.id.btnAbout)
        val btnSignOut = view.findViewById<Button>(R.id.btnSignOut)

        sessionManager = SessionManager(requireContext())
        btnAbout.setOnClickListener {

            findNavController().navigate(R.id.aboutFragment)
        }

        btnSignOut.setOnClickListener {
            sessionManager.clearSession()

            val intent = Intent(requireContext(), AuthActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or  Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        return view
    }
}
