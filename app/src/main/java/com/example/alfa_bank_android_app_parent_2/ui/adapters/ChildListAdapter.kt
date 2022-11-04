package com.example.alfa_bank_android_app_parent_2.ui.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alfa_bank_android_app_parent_2.R
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Child

class ChildListAdapter(var children:List<Child>): RecyclerView.Adapter<ChildListAdapter.ItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ChildListAdapter.ItemHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return children.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var number = itemView.findViewById<TextView>(R.id.numberTextView)
        var image = itemView.findViewById<ImageView>(R.id.itemImageView)
        var text = itemView.findViewById<TextView>(R.id.textTextView)
    }

}