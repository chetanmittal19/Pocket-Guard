package com.example.pocketguard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pocketguard.databinding.FragmentFeedbackBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FeedbackFragment : Fragment() {

    private lateinit var btnInsert: ImageView
    private lateinit var question: EditText
    private lateinit var databaseUsers: DatabaseReference
    private lateinit var list: ArrayList<User>
    private lateinit var binding: FragmentFeedbackBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentFeedbackBinding.inflate(inflater, container, false)

        fillRecycler()

        binding.apply {
            btnInsert = feedbackImage
            question = insertQuestion
        }

        databaseUsers = FirebaseDatabase.getInstance().reference

        btnInsert.setOnClickListener{
            insertData()
            question.text = null
        }

        return binding.root
    }

    private fun fillRecycler() {
        val feedbackRecycler = binding.feedbackRecycler
        val databaseReference = FirebaseDatabase.getInstance().getReference("users")
        list = arrayListOf()

        val progress = binding.progressBar
        progress.visibility = View.VISIBLE

        feedbackRecycler.layoutManager = LinearLayoutManager(requireContext())
        val adapter = FeedbackAdapter(list)

        feedbackRecycler.adapter = adapter

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()) {
                    list.clear()
                    for (dataSnapshot in snapshot.children) {
                        val user = dataSnapshot.getValue(User::class.java)
                        list.add(user!!)
                        list.sortBy {
                            it.id
                        }
                    }
                    adapter.notifyDataSetChanged()
                }
                progress.visibility = View.INVISIBLE
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun insertData(){
        val auth = FirebaseAuth.getInstance()
        val userEmail = auth.currentUser?.email
        val userQuestion = question.text.toString()
        val id = databaseUsers.push().key.hashCode().toString()
//        Toast.makeText(this, "id: $id", Toast.LENGTH_LONG).show()

        val user = User(list.size + 1, userEmail, userQuestion)
        databaseUsers.child("users").child(id).setValue(user)
            .addOnCompleteListener{task ->
                if(task.isSuccessful){
                    Toast.makeText(requireContext(), "message sent", Toast.LENGTH_SHORT).show()
                }
            }
    }
}