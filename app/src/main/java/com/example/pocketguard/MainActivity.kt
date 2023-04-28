package com.example.pocketguard

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.pocketguard.databinding.ActivityMainBinding
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (checkSmsPermission()) {
            // Get the user's current location
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        // Append the latitude and longitude to the SMS message
                        val lat = location.latitude
                        val lng = location.longitude
                        DataHolder.lt = lat
                        DataHolder.lo = lng
                        Toast.makeText(this, "Pocket Guard", Toast.LENGTH_SHORT).show()
                        Log.d("MainActivity", "FusedClientInitiated")
                    }
                }
                .addOnFailureListener { e ->
                    Log.d("EmergencyFragment", "Error getting location: $e")
                    Toast.makeText(this, "Error getting location", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkSmsPermission(): Boolean {
        return if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) true
        else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 123)
            false
        }
    }
}