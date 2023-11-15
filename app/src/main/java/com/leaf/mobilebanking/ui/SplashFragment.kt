package com.leaf.mobilebanking.ui

import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.leaf.mobilebanking.R

class SplashFragment : Fragment(R.layout.fragment_splash) {
    private val navController by lazy { findNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
            requireActivity().window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        object : CountDownTimer(2_500, 2500){
            override fun onTick(p0: Long) {
            }

            override fun onFinish() {
                navController.navigate(R.id.action_splashFragment_to_signInFragment)
                requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            }

        }.start()

    }

}