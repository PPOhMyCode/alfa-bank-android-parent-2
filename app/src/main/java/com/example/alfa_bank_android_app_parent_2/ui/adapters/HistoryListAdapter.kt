package com.example.alfa_bank_android_app_parent_2.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.alfa_bank_android_app_parent_2.R
import com.example.alfa_bank_android_app_parent_2.domain.entiies.HistoryDish
import com.squareup.picasso.Picasso

class HistoryListAdapter(
    private val items: List<Any>
) : RecyclerView.Adapter<HistoryListAdapter.ItemHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        if (viewType == 0)
            return ItemHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_history_dish, parent, false)
            )
        return ItemHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_date, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        when (val item = items[position]) {
            is String -> {
                holder.dateTextView?.text ="${item.split("T")[0].split("-")[2]} ${months[item.split("T")[0].split("-")[1].toInt()]}"
            }
            is HistoryDish -> {
                with(item) {
                    Picasso.get().load("https://storage.yandexcloud.net/systemimg/${id}.png")
                        .into(holder.dishImageView)
                    holder.dishImageView
                    holder.sumTextView?.text = "-${sum.split(".")[0]} р"
                    holder.weightTextView?.text = "${weight.split(".")[0]} г"
                    holder.nameTextViewDish?.text = name
                }
            }
            else -> throw Exception()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is String -> {
                1
            }
            is HistoryDish -> {
                0
            }
            else -> throw Exception()
        }
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameTextViewDish: TextView? = itemView.findViewById(R.id.nameTextViewDish)
        var weightTextView: TextView? = itemView.findViewById(R.id.weightTextView)
        var sumTextView: TextView? = itemView.findViewById(R.id.sumTextView)
        var dateTextView: TextView? = itemView.findViewById(R.id.dateTextView)
        var dishImageView: ImageView? = itemView.findViewById(R.id.dishImageView)
    }


    companion object{

        private val months = mapOf<Int,String>(
            1 to "Января",
            2 to "Февраля",
            3 to "Марта",
            4 to "Апреля",
            5 to "Мая",
            6 to "Июня",
            7 to "Июля",
            8 to "Августа",
            9 to "Сентября",
            10 to "Октября",
            11 to "Ноября",
            12 to "Декабря",
        )
    }
}