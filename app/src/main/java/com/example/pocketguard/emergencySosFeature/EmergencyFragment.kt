package com.example.pocketguard.emergencySosFeature

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.ContactsContract
import android.telephony.SmsManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pocketguard.DataHolder
import com.example.pocketguard.databinding.FragmentEmergencyBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EmergencyFragment : Fragment() {

    private lateinit var _binding: FragmentEmergencyBinding
    private val binding get() = _binding
    private val PERMISSION_REQUEST_CODE = 1
    private lateinit var shakeDetector: ShakeDetector
    private lateinit var database: ContactDatabase
    private var listOfContact = listOf<Contact>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEmergencyBinding.inflate(inflater, container, false)

        database = ContactDatabase.getDatabase(requireContext())

        val addContact = binding.addContactBtn
        addContact.setOnClickListener {
            if(listOfContact.size==5){
                Toast.makeText(requireContext(), "Can't add more than 5 contacts", Toast.LENGTH_SHORT).show()
            } else {
                val i = Intent(Intent.ACTION_PICK)
                i.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
                startActivityForResult(i, 111)
            }
        }

        val contactList = binding.contactList
        contactList.apply {
            layoutManager = LinearLayoutManager(requireContext())

            adapter = ContactsAdapter(::onContactClicked).apply{
                setHasStableIds(true)
            }
            setHasFixedSize(true)
        }

//        Toast.makeText(requireContext(), "${DataHolder.lt} ${DataHolder.lo}", Toast.LENGTH_SHORT).show()
        Log.d("EmergencyFragment", "${DataHolder.lt} ${DataHolder.lo}")
        getData()

        // Initialize the shake detector
        shakeDetector = ShakeDetector {
            if (it==3 && listOfContact.isNotEmpty()) {
                vibrate()
                listOfContact.forEach {ct ->
                    sendSms(ct.phoneNo)
                }
            }
        }

        return binding.root
    }

    private fun getData(){
        database.contactDao().getContact().observe(viewLifecycleOwner) {
            listOfContact = it
            (binding.contactList.adapter as ContactsAdapter).contactData = listOfContact
            Log.d("Chetan", it.toString())
        }
    }

    private fun onContactClicked(contact: Contact){
        GlobalScope.launch {
            database.contactDao().deleteContact(contact)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 111 && resultCode == Activity.RESULT_OK){
            val contactUri : Uri = data?.data ?: return
            val cols : Array<String> = arrayOf(
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)

            val rs = requireActivity().contentResolver.query(contactUri, cols, null, null, null)
            if(rs?.moveToFirst()!!){
                val phoneNo = rs.getString(0)
                val name = rs.getString(1)

                GlobalScope.launch {
                    database.contactDao().insertContact(Contact(0, name, phoneNo))
                }
            }
            rs.close()
        }
    }

    private fun vibrate() {
        val vibrator = requireActivity().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val vibEff: VibrationEffect?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            vibEff = VibrationEffect.createPredefined(VibrationEffect.EFFECT_DOUBLE_CLICK)
            vibrator.cancel()
            vibrator.vibrate(vibEff)
        } else {
            vibrator.vibrate(500)
        }
    }

    private fun sendSms(phoneNumber: String) {
        if (checkSmsPermission() && checkLocationPermission()) {
            val smsManager = SmsManager.getDefault()
            val message = "I'm in danger, help!"
            val sentIntent = PendingIntent.getBroadcast(requireContext(), 0, Intent("SMS_SENT"), PendingIntent.FLAG_IMMUTABLE)
            val deliveredIntent = PendingIntent.getBroadcast(requireContext(), 0, Intent("SMS_DELIVERED"), PendingIntent.FLAG_IMMUTABLE)
            if (phoneNumber.isEmpty()) return
            val locationStr = "Track here: https://www.google.com/maps/search/?api=1&query=${DataHolder.lt},${DataHolder.lo}"
            Log.d("EmergencySms", locationStr)
            val fullMessage = "$message\n$locationStr"
            smsManager.sendTextMessage(phoneNumber, null, fullMessage, sentIntent, deliveredIntent)
            Toast.makeText(requireContext(), "SMS sent to $phoneNumber", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()

        // Register the shake detector
        val sensorManager = requireActivity().getSystemService(AppCompatActivity.SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(shakeDetector, accelerometer, SensorManager.SENSOR_DELAY_UI)

        // Request permission to send SMS if needed
        if (!checkSmsPermission() || !checkLocationPermission()) {
            requestSmsAndLocationPermissions()
        }
    }

    override fun onPause() {
        super.onPause()
        // Unregister the shake detector
        val sensorManager = requireActivity().getSystemService(AppCompatActivity.SENSOR_SERVICE) as SensorManager
        sensorManager.unregisterListener(shakeDetector)
    }

    private fun checkSmsPermission(): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestSmsAndLocationPermissions() {
        ActivityCompat.requestPermissions(requireActivity(),
            arrayOf(
                Manifest.permission.SEND_SMS,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE
            ), PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(requireContext(), "Permissions granted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Permissions denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}