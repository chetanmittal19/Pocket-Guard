package com.example.pocketguard

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pocketguard.databinding.FragmentRateBarBinding

class RateBarFragment : Fragment() {

    private lateinit var _binding: FragmentRateBarBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRateBarBinding.inflate(inflater, container, false)

        val rt = binding.ratingBar
        val stars = rt.progressDrawable as LayerDrawable
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);

        binding.button.setOnClickListener {
            binding.textView2.text = "You rated: ${rt.rating}"
        }
        return binding.root
    }
}