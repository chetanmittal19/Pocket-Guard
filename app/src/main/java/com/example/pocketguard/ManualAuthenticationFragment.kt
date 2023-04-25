package com.example.pocketguard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.pocketguard.databinding.FragmentManualAuthenticationBinding

class ManualAuthenticationFragment : Fragment() {

    private lateinit var _binding: FragmentManualAuthenticationBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentManualAuthenticationBinding.inflate(inflater, container, false)
        val rd = binding.radioGroup
        val nextBtn = binding.next
        
        nextBtn.setOnClickListener { 
            val selectedBtn = rd.checkedRadioButtonId
            if(selectedBtn!=-1) {
                val selectedId = requireActivity().findViewById<RadioButton>(selectedBtn)
                var id = 0
                when(selectedId){
                    binding.bedroom -> { id = 0 }
                    binding.bathroom -> { id = 1 }
                    binding.changingRoom -> { id = 2 }
                    binding.outside -> { id = 3 }
                }
                findNavController().navigate(
                    ManualAuthenticationFragmentDirections.actionManualAuthenticationFragmentToManualAuthenticationDetailFragment(id)
                )
            } else Toast.makeText(requireContext(), "Please select your location", Toast.LENGTH_LONG).show()
        }
        
        return binding.root
    }

}