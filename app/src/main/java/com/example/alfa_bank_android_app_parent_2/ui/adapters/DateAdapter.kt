package com.example.alfa_bank_android_app_parent_2.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alfa_bank_android_app_parent_2.R
import com.example.alfa_bank_android_app_parent_2.domain.entiies.ReceiptsItem

class DateAdapter(
) : RecyclerView.Adapter<DateAdapter.ItemHolder>() {

    var date: List<String> = listOf()

    var onItemClickListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.date_item, parent, false)
        return ItemHolder(itemHolder)
    }

    override fun getItemCount(): Int = date.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        with(holder) {
            dateTextView.text = date[position]
        }
        holder.itemView.setOnClickListener() {
            onItemClickListener?.invoke(date[position])
        }
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var dateTextView = itemView.findViewById<TextView>(R.id.dateTextView)
    }
}