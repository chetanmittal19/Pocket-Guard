package com.example.pocketguard.hiddenCameraFeature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pocketguard.R
import com.example.pocketguard.databinding.FragmentHiddenCameraBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class HiddenCameraFragment : Fragment() {

    private lateinit var _binding: FragmentHiddenCameraBinding
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHiddenCameraBinding.inflate(inflater, container, false)

        val magnetometerBtn = binding.btnMagnetometer
        magnetometerBtn.setOnClickListener{
            findNavController().navigate(
                HiddenCameraFragmentDirections.actionHiddenCameraFragmentToMagnetometer()
            )
        }

        val detailedInstruction = binding.magnetometerInstructions
        detailedInstruction.setOnClickListener {
            val v: View = layoutInflater.inflate(R.layout.item_bottom_sheet, null)
            val dialog = BottomSheetDialog(requireContext())
            dialog.setContentView(v)
            dialog.show()
        }

        return binding.root
    }
}