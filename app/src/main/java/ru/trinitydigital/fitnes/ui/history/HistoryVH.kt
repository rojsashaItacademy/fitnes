package ru.trinitydigital.fitnes.ui.history

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_history.view.*
import ru.trinitydigital.fitnes.data.model.MainTraining

class HistoryVH(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(item: MainTraining, listener: ItemClicks) {
        itemView.tvDate.text = item.finishAt.toString()

        itemView.setOnClickListener { listener.clickOnDelete(item) }
    }
}