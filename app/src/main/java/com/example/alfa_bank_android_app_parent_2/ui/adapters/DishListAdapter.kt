package com.example.alfa_bank_android_app_parent_2.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alfa_bank_android_app_parent_2.R
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Dish
import org.w3c.dom.Text

class DishListAdapter(var dishes:List<Dish>): RecyclerView.Adapter<DishListAdapter.ItemHolder>() {
    var onItemClick:((Dish)->Unit)?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dish,parent,false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        with(dishes[position]) {
            holder.cost.text =cost.toString()
        }
        holder.addDish.setOnClickListener{
            holder.deleteDish.visibility=View.VISIBLE
            holder.addDish.visibility= View.INVISIBLE
            holder.count.visibility = View.VISIBLE
        }
        holder.deleteDish.setOnClickListener{
            holder.deleteDish.visibility=View.INVISIBLE
            holder.addDish.visibility= View.VISIBLE
            holder.cost.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return dishes.size
    }



    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cost: TextView = itemView.findViewById(R.id.cost)
        var addDish: ImageButton = itemView.findViewById(R.id.addDish)
        var deleteDish: ImageButton = itemView.findViewById(R.id.deleteDish)
        var count : TextView = itemView.findViewById(R.id.count)
    }
}