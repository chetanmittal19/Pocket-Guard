package com.example.pocketguard

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pocketguard.databinding.FragmentMagnetometerBinding
import kotlin.math.sqrt

class Magnetometer : Fragment(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var magnetometerSensor: Sensor? = null
    private lateinit var binding: FragmentMagnetometerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        magnetometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        if(magnetometerSensor == null){
            binding.reading.text = "Magnetic sensor was not found"
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentMagnetometerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onSensorChanged(event: SensorEvent) {
        val magX = event.values[0]
        val magY = event.values[1]
        val magZ = event.values[2]
        var mag = sqrt((magX * magX) + (magY * magY) + (magZ * magZ))
        binding.reading.text = "$mag" + "\u03BC Tesla"

        if(mag>180) mag = 180F
        binding.pointer.rotation = mag
        if (mag<=80) binding.pointer.setBackgroundResource(R.color.green)
        else if (mag<=110) binding.pointer.setBackgroundResource(R.color.cornflower_blue)
        else if (mag<=150) binding.pointer.setBackgroundResource(R.color.yellow)
        else binding.pointer.setBackgroundResource(R.color.red)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onResume() {
        super.onResume()
        magnetometerSensor?.also{
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

}