package com.example.recipeapp.ui.more

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.R
import com.example.recipeapp.app.AuthActivity
import com.example.recipeapp.app.SessionManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MoreBottomSheet : BottomSheetDialogFragment() {

    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.bottom_sheet_more, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // About button
        val btnAbout = view.findViewById<Button>(R.id.btnAbout)

        sessionManager = SessionManager(requireContext())

        btnAbout.setOnClickListener {
            // Navigate to AboutFragment
            findNavController().navigate(R.id.aboutFragment)
            dismiss() // Close bottom sheet
        }

        // Sign out button
        val btnSignOut = view.findViewById<Button>(R.id.btnSignOut)

        btnSignOut.setOnClickListener {
            sessionManager.clearSession()

            val intent = Intent(requireContext(), AuthActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or  Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
