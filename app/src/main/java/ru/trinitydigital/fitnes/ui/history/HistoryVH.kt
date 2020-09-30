package ru.trinitydigital.fitnes.ui.history

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_history.view.*
import ru.trinitydigital.fitnes.R
import ru.trinitydigital.fitnes.data.model.MainTraining
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class HistoryVH(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(item: MainTraining, listener: ItemClicks) {

        val date = Date(item.startAt)
        val dateend = Date(item.finishAt)

        val sdf = SimpleDateFormat("HH:mm:ss dd:M:yyyy", Locale.getDefault())

        itemView.tvDate.text =
            itemView.context.resources.getString(R.string.start_time, sdf.format(date))

        itemView.tvDateEnd.text =
            itemView.context.resources.getString(R.string.end_time, sdf.format(dateend))

        val df = DecimalFormat("0.000")// 24234234234234234234234234

        itemView.tvDistance.text = itemView.context.resources.getString(
            R.string.distance,
            df.format(item.distance)
        )


        itemView.setOnClickListener { listener.clickOnDelete(item) }
    }
}