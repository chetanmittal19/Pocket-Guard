package com.example.pocketguard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController

class HiddenCameraFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_hidden_camera, container, false)

        val magnetometerBtn = view.findViewById<CardView>(R.id.btn_magnetometer)
        magnetometerBtn.setOnClickListener{
            findNavController().navigate(
                HiddenCameraFragmentDirections.actionHiddenCameraFragmentToMagnetometer()
            )
        }
        return view
    }
}