package ru.trinitydigital.fitnes.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.trinitydigital.fitnes.R
import ru.trinitydigital.fitnes.data.model.MainTraining

class HistoryAdapter(private val listener: ItemClicks) : ListAdapter<MainTraining, HistoryVH>(DiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryVH(view)
    }

    override fun onBindViewHolder(holder: HistoryVH, position: Int) {
        holder.bind(getItem(position), listener)
    }
}

class DiffUtilCallBack : DiffUtil.ItemCallback<MainTraining>() {
    override fun areItemsTheSame(oldItem: MainTraining, newItem: MainTraining): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: MainTraining, newItem: MainTraining): Boolean {
        return oldItem.id == newItem.id
                && oldItem.finishAt == newItem.finishAt
    }
}