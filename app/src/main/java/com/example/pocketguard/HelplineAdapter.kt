package com.example.pocketguard

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class HelplineAdapter(private val listener: (Helpline, View) -> Unit): RecyclerView.Adapter<HelplineAdapter.HelplineViewHolder>() {

    var helplineData = arrayOf<Helpline>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    inner class HelplineViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.helpline_name)

        fun bind(helpline: Helpline){
            with(helpline){
                this@HelplineViewHolder.name.text = name
                itemView.setOnClickListener{
                    listener.invoke(helpline, it)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelplineAdapter.HelplineViewHolder {
        val itemLayout = LayoutInflater.from(parent.context).inflate(R.layout.helpline_item, parent, false)
//        val color = Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
//        itemLayout.setBackgroundColor(color)
        return HelplineViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: HelplineAdapter.HelplineViewHolder, position: Int) {
        val helpline = helplineData[position]
        helpline.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int {
        return helplineData.size
    }
}