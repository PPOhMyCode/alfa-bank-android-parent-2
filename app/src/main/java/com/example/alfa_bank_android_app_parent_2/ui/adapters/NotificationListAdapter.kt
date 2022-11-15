package com.example.alfa_bank_android_app_parent_2.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.alfa_bank_android_app_parent_2.R
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Dish
import com.example.alfa_bank_android_app_parent_2.domain.entiies.Notification
import java.sql.Time
import java.time.DayOfWeek

class NotificationListAdapter(var notifications: List<Notification>) :
    RecyclerView.Adapter<NotificationListAdapter.ItemHolder>() {

    var onItemClick: ((Notification) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        with(notifications[position]) {
            holder.time.text = getStringTime(time)
            holder.days.text = getStringDay(daysOfWeek)
            //holder.switch.isChecked = true
        }

        //notifyItemChanged(position)
        //holder.addDish.setOnClickListener{
        //}
    }

    override fun getItemViewType(position: Int): Int {
        return 1
    }

    override fun getItemCount(): Int {
        return notifications.size
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var time: TextView = itemView.findViewById(R.id.timeTextView)
        var days: TextView = itemView.findViewById(R.id.daysTextView)
        var switch: SwitchCompat = itemView.findViewById(R.id.switchCompat)
    }

    private fun getStringTime(time: Time) =
        String.format("%02d", time.hours) + " : " + String.format("%02d", time.minutes)

    private fun getStringDay(daysOfWeek: List<DayOfWeek>): String {
        if(daysOfWeek.size==7)
            return "Ежедневно"
        val result = StringBuilder()
        with(result) {
            for (day in daysOfWeek) {
                when (day) {
                    DayOfWeek.MONDAY -> append("ПН ")
                    DayOfWeek.TUESDAY -> append("ВТ ")
                    DayOfWeek.WEDNESDAY -> append("СР ")
                    DayOfWeek.THURSDAY -> append("ЧТ ")
                    DayOfWeek.FRIDAY -> append("ПТ ")
                    DayOfWeek.SATURDAY -> append("СБ ")
                    DayOfWeek.SUNDAY -> append("ВС ")
                }
            }
        }
        return result.toString()
    }
}