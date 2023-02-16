package com.example.pocketguard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.view.View
//import android.widget.Toolbar
//import androidx.appcompat.app.ActionBarDrawerToggle
//import androidx.core.view.GravityCompat
//import androidx.drawerlayout.widget.DrawerLayout
//import androidx.navigation.Navigation
//import androidx.navigation.findNavController
////import androidx.navigation.findNavController
//import androidx.navigation.fragment.findNavController
//import androidx.navigation.ui.setupWithNavController
import com.example.pocketguard.databinding.ActivityMainBinding
//import com.google.android.material.navigation.NavigationView
//import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
//    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.apply {
//            toggle = ActionBarDrawerToggle(this@MainActivity, drawerLayout, R.string.open, R.string.close)
//            drawerLayout.addDrawerListener(toggle)
//            toggle.syncState()
//
//            supportActionBar?.setDisplayHomeAsUpEnabled(true)
//
//            navigationView.setNavigationItemSelectedListener {
//                when (it.itemId) {
////                R.id.menu_chat -> findNavController().navigate(
////                )
//                    R.id.contact ->  findNavController().navigate(
//                        FeaturesFragmentDirections.actionFeaturesFragmentToContactFragment()
//                    )
//                    R.id.about ->  findNavController().navigate(
//                        FeaturesFragmentDirections.actionFeaturesFragmentToAboutFragment()
//                    )
//                    R.id.sign_out -> {
//                        val auth = FirebaseAuth.getInstance()
//                        auth.signOut()
//                        auth.addAuthStateListener {
//                            if(auth.currentUser == null) {
//                                //listener is called multiple times so check if we are in correct fragment
//                                val currId = findNavController().currentDestination!!.id
//                                if(currId == R.id.featuresFragment) {
//                                    findNavController().navigate(
//                                        FeaturesFragmentDirections.actionFeaturesFragmentToLoginFragment()
//                                    )
//                                }
//                            }
//                        }
//                    }
//                }
//                true
//            }
//        }
    }
}