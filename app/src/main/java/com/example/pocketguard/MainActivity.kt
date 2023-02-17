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

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}