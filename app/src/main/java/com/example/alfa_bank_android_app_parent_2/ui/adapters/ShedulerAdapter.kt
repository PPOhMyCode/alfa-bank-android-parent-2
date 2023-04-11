package com.example.alfa_bank_android_app_parent_2.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alfa_bank_android_app_parent_2.R
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Schedule

class ShedulerAdapter(private val shedulers: List<Schedule>?=null) :
    RecyclerView.Adapter<ShedulerAdapter.ItemHolder>() {

    var onItemClickListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sheduler, parent, false)
        return ItemHolder(itemHolder)
    }

    override fun getItemCount(): Int = 3

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
      //  with(holder) {
      //      timeDish.text = shedulers[position].title
      //      descriptionDish.text = shedulers[position].description
      //  }
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //var timeDish = itemView.findViewById<TextView>(R.id.textView21)
        //var descriptionDish = itemView.findViewById<TextView>(R.id.textView22)
    }

}