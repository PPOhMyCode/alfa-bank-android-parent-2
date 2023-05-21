package com.example.alfa_bank_android_app_parent_2.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alfa_bank_android_app_parent_2.R
import com.example.alfa_bank_android_app_parent_2.domain.entiies.ScheduleItem
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class ShedulerAdapter() :
    RecyclerView.Adapter<ShedulerAdapter.ItemHolder>() {

    var onItemClickListener: ((String) -> Unit)? = null
    var shedulers: List<ScheduleItem>?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sheduler, parent, false)
        return ItemHolder(itemHolder)
    }

    override fun getItemCount(): Int = shedulers?.size?:0

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        shedulers?.let {
            with(holder) {
                titleDish.text = it[position].description
                timeDish.text = it[position].title
                if(it[position].description == "Завтрак") {
                    Picasso.get().load("https://storage.yandexcloud.net/systemimg/typeMealButtons/1.png").into(image)
                }
                else if(it[position].description == "Полдник"){
                    Picasso.get().load("https://storage.yandexcloud.net/systemimg/typeMealButtons/3.png").into(image)
                }else{
                    Picasso.get().load("https://storage.yandexcloud.net/systemimg/typeMealButtons/2.png").into(image)
                }
            }
        }
      //  with(holder) {
      //      timeDish.text = shedulers[position].title
      //      descriptionDish.text = shedulers[position].description
      //  }
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById<ImageView>(R.id.timeImage)
        var timeDish = itemView.findViewById<TextView>(R.id.timeTextView)
        var titleDish = itemView.findViewById<TextView>(R.id.titleTime)
    }

}