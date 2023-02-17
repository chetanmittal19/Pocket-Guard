package com.example.pocketguard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FeedbackAdapter(private val list: ArrayList<User>): RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder>() {

    inner class FeedbackViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val email: TextView = view.findViewById(R.id.textemail)
        private val question = view.findViewById<TextView>(R.id.textquestion)

        fun bind(item: User){
            with(item){
                this@FeedbackViewHolder.email.text = email
                this@FeedbackViewHolder.question.text = question
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