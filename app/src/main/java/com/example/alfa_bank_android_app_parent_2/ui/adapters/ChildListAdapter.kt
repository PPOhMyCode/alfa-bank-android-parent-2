package com.example.alfa_bank_android_app_parent_2.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alfa_bank_android_app_parent_2.R
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Child

class ChildListAdapter(var children: List<Child>) :
    RecyclerView.Adapter<ChildListAdapter.ItemHolder>() {
    var onItemClick: ((Child)->Unit)?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_child,parent,false)
        return ItemHolder(view)
    }

    override fun getItemCount(): Int {
        return children.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        with(children[position]) {
            holder.name.text = name
            holder.schoolClass.text = schoolClass
            holder.school.text = school
            holder.account.text = account.toString()
        }
        holder.itemView.setOnClickListener{
            onItemClick?.invoke(children[position])
        }
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name = itemView.findViewById<TextView>(R.id.nameTextView)
        var schoolClass = itemView.findViewById<TextView>(R.id.schoolClassTextView)
        var school = itemView.findViewById<TextView>(R.id.schoolTextView)
        var account = itemView.findViewById<TextView>(R.id.accountTextView)
    }

}