package com.example.pocketguard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FeaturesAdapter(private val onFeatureClicked: (Feature) -> Unit) : RecyclerView.Adapter<FeaturesAdapter.FeatureViewHolder>() {

    var featureData = arrayOf<Feature>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    inner class FeatureViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.icon)
        val name: TextView = view.findViewById(R.id.name)

        fun bind(feature: Feature){
            with(feature){
                icon.setImageResource(iconId)
                this@FeatureViewHolder.name.text = name
                itemView.setOnClickListener{
                    onFeatureClicked.invoke(feature)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return featureData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureViewHolder {
        val itemLayout = LayoutInflater.from(parent.context).inflate(R.layout.features_item, parent, false)

        return FeatureViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: FeatureViewHolder, position: Int) {
//        with(holder){
//            icon.setImageResource(featureData[position].iconId)
//            name.text = featureData[position].name
//        }
        val feature = featureData[position]
        feature.let{
            holder.bind(it)
        }
    }
}