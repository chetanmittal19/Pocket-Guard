package com.example.pocketguard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pocketguard.databinding.FragmentFeaturesBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController

class FeaturesFragment : Fragment() {
    private lateinit var _binding: FragmentFeaturesBinding
    private val binding get() = _binding!!

    private val features = arrayOf(
        Feature(1, "Emergency SOS", R.drawable.sos),
        Feature(2, "Hidden Camera", R.drawable.camera),
        Feature(3, "Siren Alert", R.drawable.siren), //done
        Feature(4, "Current Location", R.drawable.location),
        Feature(5, "Helpline Numbers", R.drawable.helpline),
//        Feature(6, "Find My Phone", R.drawable.search),
        Feature(7, "Safety Tips", R.drawable.safety),
        Feature(8, "Community", R.drawable.feedback))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFeaturesBinding.inflate(inflater, container, false)
//        val view = inflater.inflate(R.layout.fragment_features, container, false)

        val fragmentList = binding.featuresList
        fragmentList.apply{
            layoutManager = GridLayoutManager(activity, 2)

            adapter = FeaturesAdapter(::onFeatureClick).apply {
                setHasStableIds(true)
            }
            setHasFixedSize(true)
        }

        (fragmentList.adapter as FeaturesAdapter).featureData = features

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNavigationDrawer()
    }

    private fun setupNavigationDrawer() {
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        val drawerLayout = requireActivity().findViewById<DrawerLayout>(R.id.drawerLayout)
        val navigationView = requireActivity().findViewById<NavigationView>(R.id.navigationView)
        val toolbar = binding.toolbar

        NavigationUI.setupWithNavController(toolbar, navController,
            AppBarConfiguration.Builder(R.id.navigation, R.id.featuresFragment)
                .setDrawerLayout(drawerLayout)
                .build()
        )

        navigationView.setupWithNavController(navController)

        navigationView.setNavigationItemSelectedListener {
            drawerLayout.closeDrawers()

            when (it.itemId) {
                R.id.tip -> findNavController().navigate(
                    FeaturesFragmentDirections.actionFeaturesFragmentToManualAuthenticationFragment()
                )
                R.id.menu_chat -> findNavController().navigate(
                    FeaturesFragmentDirections.actionFeaturesFragmentToFeedbackFragment()
                )
                R.id.contact ->  findNavController().navigate(
                    FeaturesFragmentDirections.actionFeaturesFragmentToContactFragment()
                )
                R.id.about ->  findNavController().navigate(
                    FeaturesFragmentDirections.actionFeaturesFragmentToAboutFragment()
                )
                R.id.sign_out -> {
                    val auth = FirebaseAuth.getInstance()
                    Toast.makeText(requireContext(), "name: " + auth.currentUser?.email, Toast.LENGTH_LONG).show()
                    auth.signOut()
                    auth.addAuthStateListener {
                        if(auth.currentUser == null) {
                            //listener is called multiple times so check if we are in correct fragment
                            val currId = findNavController().currentDestination!!.id
                            if(currId == R.id.featuresFragment) {
                                findNavController().navigate(
                                    FeaturesFragmentDirections.actionFeaturesFragmentToLoginFragment()
                                )
                            }
                        }
                    }
                }
            }
            true
        }
    }

    private fun onFeatureClick(feature: Feature){
        Toast.makeText(requireContext(), "Fragment: ${feature.name}", Toast.LENGTH_SHORT).show()
        when(feature.id){
            1->findNavController().navigate(FeaturesFragmentDirections.actionFeaturesFragmentToEmergencyFragment())
            2->findNavController().navigate(FeaturesFragmentDirections.actionFeaturesFragmentToHiddenCameraFragment())
            3->findNavController().navigate(FeaturesFragmentDirections.actionFeaturesFragmentToFlashingFragment())
            4-> startActivity(Intent(this@FeaturesFragment.requireContext(), LocationAct::class.java))
            5->findNavController().navigate(FeaturesFragmentDirections.actionFeaturesFragmentToHelplineFragment())
            6->"abc"
            7->findNavController().navigate(FeaturesFragmentDirections.actionFeaturesFragmentToTipsFragment())
            8->findNavController().navigate(FeaturesFragmentDirections.actionFeaturesFragmentToFeedbackFragment())
        }
    }
}