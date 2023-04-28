package com.example.pocketguard

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class FeedbackAdapter(private val list: ArrayList<User>): RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder>() {

    companion object{
        private val emailAuth = FirebaseAuth.getInstance().currentUser?.email
    }
    inner class FeedbackViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val email: TextView = view.findViewById(R.id.textemail)
        private val question = view.findViewById<TextView>(R.id.textquestion)

        fun bind(item: User){
            with(item){
                this@FeedbackViewHolder.email.text = "~ $email"
                this@FeedbackViewHolder.question.text = question
                if(email.equals("pocketguard@gmail.com")){
                    this@FeedbackViewHolder.email.setBackgroundColor(Color.parseColor("#000000"))
//                    this@FeedbackViewHolder.question.setBackgroundColor(Color.parseColor("#F6BE00"))
                } else if(emailAuth.equals(email)){
                    this@FeedbackViewHolder.email.setBackgroundColor(Color.parseColor("#5A5A5A"))
                    this@FeedbackViewHolder.question.setBackgroundColor(Color.parseColor("#8B0000"))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedbackAdapter.FeedbackViewHolder {
        val itemLayout = LayoutInflater.from(parent.context).inflate(R.layout.feedback_item, parent, false)
        return FeedbackViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: FeedbackAdapter.FeedbackViewHolder, position: Int) {
        val item = list[position]
        item.let{
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}