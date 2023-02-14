package com.example.pocketguard

import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar

const val PERMISSION_REQUEST_COARSE_LOCATION = 0
class LocationAct : AppCompatActivity(), OnMapReadyCallback {

    var x = 28.629717
    var y = 77.207065
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        requestLastLocation()

        val loc = LatLng(x, y)
        googleMap.addMarker(
            MarkerOptions().position(loc)
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc))
    }
    
    private fun getLastLocationAddress(){
        if(ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) == 
                PackageManager.PERMISSION_GRANTED) {
            requestLastLocation()
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission()
            return
        } else {
            fusedLocationProviderClient?.lastLocation
                ?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful && task.result != null) {
                        task.result?.let {
        //                        last_location_address.text = "Latitude: {it.latitude} Longitude: {it.longitude}"
                            x = it.latitude
                            y = it.longitude
                        }
                    } else {
                        Toast.makeText(this, "The permission to access location was denied", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                ACCESS_COARSE_LOCATION)) {
            val snack = Snackbar.make(R.id.container as View, "The location permission is required for the app to\n" +
                    "function properly. Please grant the permission.", Snackbar.LENGTH_INDEFINITE)
            snack.setAction("OK") {
                ActivityCompat.requestPermissions(this,
                    arrayOf(ACCESS_COARSE_LOCATION),
                    PERMISSION_REQUEST_COARSE_LOCATION)
            }
            snack.show()
        } else {
            ActivityCompat.requestPermissions(this,
                arrayOf(ACCESS_COARSE_LOCATION),
                PERMISSION_REQUEST_COARSE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == PERMISSION_REQUEST_COARSE_LOCATION) {
            if(grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestLastLocation()
            } else {
                Toast.makeText(this, "The permission to accesss location was denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}