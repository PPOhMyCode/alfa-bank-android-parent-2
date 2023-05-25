package com.example.alfa_bank_android_app_parent_2.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alfa_bank_android_app_parent_2.R
import com.example.alfa_bank_android_app_parent_2.domain.entiies.ReceiptsItem
import org.w3c.dom.Text

class ReceiptsAdapter(
) : RecyclerView.Adapter<ReceiptsAdapter.ItemHolder>() {

    var onItemClickListener: ((ReceiptsItem) -> Unit)? = null
    var receipts: List<ReceiptsItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_receipts, parent, false)
        return ItemHolder(itemHolder)
    }

    override fun getItemCount(): Int = receipts.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        with(holder) {
            month.text = receipts[position].month
            year.text = receipts[position].year
        }
        holder.itemView.setOnClickListener(){
            onItemClickListener?.invoke(receipts[position])
        }
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var month = itemView.findViewById<TextView>(R.id.month)
        var year = itemView.findViewById<TextView>(R.id.year)
    }
}