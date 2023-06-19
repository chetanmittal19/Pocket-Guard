package com.example.pocketguard.authenticationFeature

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.pocketguard.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth


class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val login = view.findViewById<Button>(R.id.login)
        login.setOnClickListener{
            findNavController().navigate(
                RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            )
        }

        val register = view.findViewById<Button>(R.id.register)
        register.setOnClickListener{
            view.findViewById<TextInputLayout>(R.id.user_email_container).error = null
            view.findViewById<TextInputLayout>(R.id.user_pass_container).error = null

            val email = view.findViewById<TextInputEditText>(R.id.user_email).text.toString()
            val pass = view.findViewById<TextInputEditText>(R.id.user_pass).text.toString()

            if(validateInput(email, pass, view)){
                val progress = view.findViewById<ProgressBar>(R.id.progress)
                progress.visibility = View.VISIBLE

                auth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(requireActivity()){task ->
                        progress.visibility = View.INVISIBLE
                        if(task.isSuccessful){
                            Toast.makeText(requireContext(), "Authentication Successful", Toast.LENGTH_SHORT).show()
                            RegisterFragmentDirections.actionRegisterFragmentToFeaturesFragment()
                        } else {
                            val toast = Toast.makeText(
                                requireActivity(),
                                "Authentication failed: ${task.exception?.message}",
                                Toast.LENGTH_LONG
                            )

                            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
                            toast.show()
                        }
                    }
            }
        }
    }

    private fun validateInput(email: String, pass: String, view: View): Boolean{
        var valid = true
        val userEmailContainer = view.findViewById<TextInputLayout>(R.id.user_email_container)
        val userPassContainer = view.findViewById<TextInputLayout>(R.id.user_pass_container)

        if(email.isBlank()){
            userEmailContainer.error = "Please enter an email address"
            valid = false
        }
        if(pass.isBlank()){
            userPassContainer.error = "Please enter a password"
            valid = false
        } else if(pass.length < 8){
            userPassContainer.error = "Password should be 8 characters or more"
            valid = false
        }
        return valid
    }
}