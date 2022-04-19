package com.arifwidayana.challengechapter5.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.arifwidayana.challengechapter5.R
import com.arifwidayana.challengechapter5.model.utils.Constant
import com.arifwidayana.challengechapter5.model.utils.SharedHelper

@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment() {
    lateinit var shared: SharedHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shared = SharedHelper(requireContext())
        Handler(Looper.getMainLooper()).postDelayed({
            when {
                onBoardingDone() -> findNavController().navigate(R.id.action_splashScreen_to_loginFragment)
                else -> findNavController().navigate(R.id.action_splashScreen_to_onBoarding)
            }
        },3000)
    }

    private fun onBoardingDone(): Boolean {
        return shared.getBoolean(Constant.FINISHED, false)
    }
}