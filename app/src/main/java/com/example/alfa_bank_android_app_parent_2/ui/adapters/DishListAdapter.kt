package com.example.alfa_bank_android_app_parent_2.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.alfa_bank_android_app_parent_2.R
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Dish
import com.example.alfa_bank_android_app_parent_2.ui.menu.MenuFragment
import com.squareup.picasso.Picasso

class DishListAdapter(var mode:String) : RecyclerView.Adapter<DishListAdapter.ItemHolder>() {
    var dishes: List<Dish> = listOf()
    var dishCount: Map<Int, Int> = mapOf()
    var onAddItemClick: ((Dish) -> Unit)? = null
    var onDeleteItemClick: ((Dish) -> Unit)? = null
    var onImageItemClick: ((Dish) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dish, parent, false)
        return ItemHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        with(dishes[position]) {
            holder.cost.text = "${cost.toInt()} р"
            holder.nameDish.text = name
            Picasso.get().load("https://storage.yandexcloud.net/systemimg/${id}.png").into(holder.dishImage)
            //holder.dishImage
        }
        val count =  dishCount[dishes[position].id] ?: 0
        if(count>0) {
            holder.deleteDish.visibility = View.VISIBLE
            if(count> 1){
                holder.deleteDish.setImageResource(R.drawable.ic_minous)
            }else{
                holder.deleteDish.setImageResource(R.drawable.ic_trash)
            }
            holder.count.visibility = View.VISIBLE
            holder.count.text="$count шт"
            holder.cost.visibility = View.VISIBLE
        }else{
            holder.deleteDish.visibility = View.INVISIBLE
            holder.count.visibility = View.GONE
            holder.cost.visibility = View.VISIBLE
        }
        holder.dishImage.setOnClickListener {
            onImageItemClick?.invoke(dishes[position])
        }
        holder.addDish.setOnClickListener {
            if((dishCount[dishes[position].id] ?: 0) <3){
            onAddItemClick?.invoke(dishes[position])
            holder.deleteDish.visibility = View.VISIBLE
            if((dishCount[dishes[position].id] ?: 0) > 1){
                holder.deleteDish.setImageResource(R.drawable.ic_minous)
            }else{
                holder.deleteDish.setImageResource(R.drawable.ic_trash)
            }
            holder.deleteDish.visibility = View.VISIBLE
            holder.count.visibility = View.VISIBLE
            holder.count.text="${dishCount[dishes[position].id]} шт"
            }
        }
        holder.deleteDish.setOnClickListener {
            onDeleteItemClick?.invoke(dishes[position])
            if((dishCount[dishes[position].id] ?: 0) > 1){
                holder.deleteDish.setImageResource(R.drawable.ic_minous)
            }else{
                holder.deleteDish.setImageResource(R.drawable.ic_trash)
            }
            if((dishCount[dishes[position].id] ?: 0) <=0) {
                holder.deleteDish.visibility = View.INVISIBLE
                holder.count.visibility = View.GONE
            }
            holder.count.text="${dishCount[dishes[position].id]} шт"
        }

        if(mode == MenuFragment.CHOOSE_MENU_MODE){
            holder.countAndCostInformation.visibility = View.GONE
            holder.addDish.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return dishes.size
    }


    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cost: TextView = itemView.findViewById(R.id.cost)
        var addDish: ImageButton = itemView.findViewById(R.id.addDish)
        var deleteDish: ImageButton = itemView.findViewById(R.id.deleteDish)
        var count: TextView = itemView.findViewById(R.id.count)
        var nameDish: TextView = itemView.findViewById(R.id.nameDishTextView)
        var dishImage: ImageView = itemView.findViewById(R.id.dishImageView)
        var countAndCostInformation: CardView = itemView.findViewById(R.id.countAndCostInformation)
    }
}