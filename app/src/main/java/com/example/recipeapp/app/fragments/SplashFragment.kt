package com.example.recipeapp.app.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import  com.example.recipeapp.R
import android.os.Handler
import android.os.Looper
import com.example.recipeapp.app.MainActivity
import com.example.recipeapp.app.SessionManager


class SplashFragment : Fragment(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val splashAnim = view.findViewById<LottieAnimationView>(R.id.splashAnimation)
        val sessionManager = SessionManager(requireContext())

        Handler(Looper.getMainLooper()).postDelayed({
            if (sessionManager.fetchAuthToken() != null) {
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            } else {
                findNavController().navigate(R.id.action_splash_to_login)
            }
        }, 4000)
    }
}