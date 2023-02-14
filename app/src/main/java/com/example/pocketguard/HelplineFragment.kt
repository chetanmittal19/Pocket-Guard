package com.example.pocketguard

import android.content.Intent
import android.content.Intent.ACTION_CALL
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

const val PERMISSION_REQUEST_PHONE_CALL = 0

class HelplineFragment : Fragment() {
    private val helpline = arrayOf(
        Helpline("Fire and Rescue", "101"),
        Helpline("Child Helpline", "1098"),
        Helpline("Women Helpline", "1091"),
        Helpline("Ambulance", "108"),
        Helpline("Police", "100"),
        Helpline("Helpdesk", "18001802128"),
        Helpline("COVID-19 Helpline", "8558893911"),
        Helpline("Cyber Crime", "155620"))

    private var contactDetail: Helpline? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_helpline, container, false)

        val helplineList = view.findViewById<RecyclerView>(R.id.helplineRecycler)
        helplineList.apply {
            layoutManager = LinearLayoutManager(requireContext())

            adapter = HelplineAdapter(::onContactClick).apply {
                setHasStableIds(true)
            }
            setHasFixedSize(true)

        }
        (helplineList.adapter as HelplineAdapter).helplineData = helpline

        return view
    }

    private fun onContactClick(helpline: Helpline, view: View) {
        contactDetail = helpline
        makePhoneCallAfterPermission(view)
    }

    private fun makePhoneCallAfterPermission(view: View) {
        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CALL_PHONE) ==
                PackageManager.PERMISSION_GRANTED) {
            makePhoneCall()
        } else {
            requestCallPermission(view)
        }
    }

    private fun requestCallPermission(view: View) {
        if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), android.Manifest.permission.CALL_PHONE)){
            val snack = Snackbar.make(view, "We need your permission to make a call. " +
            "When asked please give the permission", Snackbar.LENGTH_INDEFINITE)
            snack.setAction("OK"){
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CALL_PHONE),
                PERMISSION_REQUEST_PHONE_CALL)
            }
            snack.show()
        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CALL_PHONE),
                PERMISSION_REQUEST_PHONE_CALL)
        }
    }

    private fun makePhoneCall() {
        val intent = Intent().apply {
            action = ACTION_CALL
            data = Uri.parse("tel:"+contactDetail?.contact)
        }
        startActivity(intent)
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == PERMISSION_REQUEST_PHONE_CALL){
            if(grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall()
            } else {
                Toast.makeText(activity, "Permission denied to make phone call", Toast.LENGTH_SHORT).show()
            }
        }
    }
}