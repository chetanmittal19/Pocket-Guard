package com.example.pocketguard.emergencySosFeature

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketguard.R

class ContactsAdapter(private val onContactClicked: (Contact) -> Unit) : RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {

    var contactData = listOf<Contact>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    inner class ContactViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val contactName: TextView = view.findViewById(R.id.contact_name)
        private val contactPhone: TextView = view.findViewById(R.id.contact_phone)

        fun bind(contact: Contact){
            with(contact){
                contactName.text = name
                contactPhone.text = phoneNo
                itemView.findViewById<ImageButton>(R.id.contact_delete).setOnClickListener {
                    onContactClicked.invoke(contact)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val itemLayout = LayoutInflater.from(parent.context).inflate(R.layout.contact_list_item, parent, false)

        return ContactViewHolder(itemLayout)
    }

    override fun getItemCount(): Int {
        return contactData.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contactData[position]
        contact.let {
            holder.bind(it)
        }
    }
}