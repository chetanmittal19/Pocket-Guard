package com.example.pocketguard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FeaturesFragment : Fragment() {
    private val features = arrayOf(
        Feature(1, "Emergency SOS", R.drawable.sos),
        Feature(2, "Hidden Camera", R.drawable.camera),
        Feature(3, "Siren Alert", R.drawable.siren), //done
        Feature(4, "Current Location", R.drawable.location), //1
        Feature(5, "Helpline Numbers", R.drawable.helpline), //2
        Feature(6, "Find My Phone", R.drawable.search),
        Feature(7, "Safety Tips", R.drawable.safety), //done
        Feature(8, "Feedback", R.drawable.feedback)) //3

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_features, container, false)

        val fragmentList = view.findViewById<RecyclerView>(R.id.featuresList)
        fragmentList.apply{
            layoutManager = GridLayoutManager(activity, 2)

            adapter = FeaturesAdapter(::onFeatureClick).apply {
                setHasStableIds(true)
            }
            setHasFixedSize(true)
        }

        (fragmentList.adapter as FeaturesAdapter).featureData = features

        return view
    }

    private fun onFeatureClick(feature: Feature){
        Toast.makeText(requireContext(), "Fragment: ${feature.name}", Toast.LENGTH_SHORT).show()
        when(feature.id){
            1->findNavController().navigate(FeaturesFragmentDirections.actionFeaturesFragmentToEmergencyFragment())
            2->findNavController().navigate(FeaturesFragmentDirections.actionFeaturesFragmentToHiddenCameraFragment())
            3->findNavController().navigate(FeaturesFragmentDirections.actionFeaturesFragmentToFlashingFragment())
            4-> startActivity(Intent(this@FeaturesFragment.requireContext(), LocationAct::class.java))
            5->findNavController().navigate(FeaturesFragmentDirections.actionFeaturesFragmentToHelplineFragment())
            6->"abs"
            7->findNavController().navigate(FeaturesFragmentDirections.actionFeaturesFragmentToTipsFragment())
            8->"abs"
        }
    }
}