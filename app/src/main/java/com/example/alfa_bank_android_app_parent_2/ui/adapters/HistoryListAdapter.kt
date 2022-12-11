package com.example.alfa_bank_android_app_parent_2.ui.adapters

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

class HistoryListAdapter(
    private val dates: List<String>,
    private val historyDish: List<HistoryDish>,
    private val items: List<Pair<Int,Int>>
) : RecyclerView.Adapter<HistoryListAdapter.ItemHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        if (viewType == 0)
            return ItemHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_history_dish, parent, false)
            )
        return ItemHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_date, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        if(items[position].first==0){
            val positionItem = items[position].second
            with(historyDish[positionItem]) {
                holder.sumTextView?.text = sum
                holder.weightTextView?.text = weight
                holder.nameTextViewDish?.text = name
            }
        }else{
            holder.dateTextView?.text = dates[items[position].second]
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].first
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameTextViewDish: TextView? = itemView.findViewById(R.id.nameTextViewDish)
        var weightTextView: TextView? = itemView.findViewById(R.id.weightTextView)
        var sumTextView: TextView? = itemView.findViewById(R.id.sumTextView)
        var dateTextView: TextView? = itemView.findViewById(R.id.dateTextView)
    }

}