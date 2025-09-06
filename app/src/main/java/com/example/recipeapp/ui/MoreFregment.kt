package com.example.recipeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.R

class MoreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_more, container, false)

        val btnAbout = view.findViewById<Button>(R.id.btnAbout)
        val btnSignOut = view.findViewById<Button>(R.id.btnSignOut)


        btnAbout.setOnClickListener {
            findNavController().navigate(R.id.aboutFragment)
        }


        btnSignOut.setOnClickListener {
        }

        return view
    }
}
