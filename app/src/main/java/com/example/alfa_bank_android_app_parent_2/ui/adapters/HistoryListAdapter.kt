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
                holder.dateTextView?.text = item.split("T")[0]
            }
            is HistoryDish -> {
                with(item) {
                    Picasso.get().load("https://storage.yandexcloud.net/systemimg/${id}.png")
                        .into(holder.dishImageView)
                    holder.dishImageView
                    holder.sumTextView?.text = "-$sum"
                    holder.weightTextView?.text = weight
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

}