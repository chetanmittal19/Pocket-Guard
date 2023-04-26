package com.example.pocketguard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pocketguard.databinding.FragmentManualAuthenticationDetailBinding

class ManualAuthenticationDetailFragment : Fragment() {

    private lateinit var _binding: FragmentManualAuthenticationDetailBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentManualAuthenticationDetailBinding.inflate(inflater, container, false)

        val tipDatabase = arrayOf("Bedroom Instructions", "Bathroom Instructions", "Changing Room Instructions", "Outside Instructions")

        val bed = "Possibility of Hidden Camera in bedroom is almost 15%.\n" +
                "\n" +
                "Scan for devices listed below:\n" +
                "\n" +
                "1. Smoke Detector -\n" +
                "Precaution: Apply sticker/tape over the visible parts of the detector.\n" +
                "\n" +
                "2. Air Conditioner -\n" +
                "Precaution: Use fan and turn OFF the AC if possible. Turn rotating blades OFF.\n" +
                "\n" +
                "3. Television -\n" +
                "Precaution: Turn off main supply, keep objects infront of lens looking part of the TV.\n" +
                "\n" +
                "4. Night Lamp -\n" +
                "Precaution: Keep it OFF or change it's position.\n" +
                "\n" +
                "5. Flower Pot -\n" +
                "Precaution: Change the position to least viewable area.\n" +
                "\n" +
                "6. Coffee Maker -\n" +
                "Precaution: Unplug it and put it inside cupboard.\n" +
                "\n" +
                "Finally Do Not Forget to turn off the lights."

        val bath = "Possibility of a Hidden Camera in bathroom is almost 12%.\n" +
                "\n" +
                "Scan for devices listed below:\n" +
                "\n" +
                "1. Water Heater -\n" +
                "Precaution: Turn it OFF before taking bath.\n" +
                "\n" +
                "2. Mirror -\n" +
                "Precaution: Touch the mirror. Do you feel any gap between your finger and it's reflection? If No, there could be a hidden camera behind the mirror glass.\n" +
                "\n" +
                "3. Ceiling/Smoke Detector -\n" +
                "Precaution: Apply sticker/tape over the visible parts of the detector.\n" +
                "\n" +
                "4. Lamps or bulbs -\n" +
                "Precaution: You cannot do much about that, but still use curtains whenever possible."

        val ch = "Possibility of a Hidden Camera in Changing Room is almost 20%.\n" +
                "\n" +
                "Scan for devices listed below:\n" +
                "\n" +
                "1. Mirror -\n" +
                "Precaution: Touch the mirror. Do you feel any gap between your finger and it's reflection? If No, there could be a hidden camera behind the mirror glass.\n" +
                "\n" +
                "2. Hanger -\n" +
                "Precaution: Check for lens looking aperture for hanger specially screw looking objects. Put clothes covering all screws.\n" +
                "\n" +
                "3. Ceiling/Smoke Detector -\n" +
                "Precaution: Apply sticker/tape over the visible parts of the detector."

        val ot = "While outside you might be under CCTV camera.\n" +
                "\n" +
                "In Outside world, you are not supposed to hide your identity in official places, so just be cautious and behave accordingly."

        val tipInstructions = arrayOf(bed, bath, ch, ot)

        var id = 0
        arguments?.let {
            val args = ManualAuthenticationDetailFragmentArgs.fromBundle(it)
            id = args.id
        }

        binding.title.text = tipDatabase[id]
        binding.instructions.text = tipInstructions[id]
        return binding.root
    }
}