package com.example.recipeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MoreBottomSheet : BottomSheetDialogFragment() {

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
        btnAbout.setOnClickListener {
            // Navigate to AboutFragment
            findNavController().navigate(R.id.aboutFragment)
            dismiss() // Close bottom sheet
        }

        // Sign out button
        val btnSignOut = view.findViewById<Button>(R.id.btnSignOut)
        btnSignOut.setOnClickListener {
            Toast.makeText(requireContext(), "Signed out", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }
}
