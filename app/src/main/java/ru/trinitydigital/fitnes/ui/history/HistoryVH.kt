package ru.trinitydigital.fitnes.ui.history

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_history.view.*
import ru.trinitydigital.fitnes.data.model.MainTraining
import java.text.SimpleDateFormat
import java.util.*

class HistoryVH(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(item: MainTraining, listener: ItemClicks) {

        val date = Date(item.startAt)

        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

        itemView.tvDate.text = sdf.format(date)

        itemView.setOnClickListener { listener.clickOnDelete(item) }
    }
}