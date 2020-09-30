package ru.trinitydigital.fitnes.ui.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.trinitydigital.fitnes.FitnessApp
import ru.trinitydigital.fitnes.R
import ru.trinitydigital.fitnes.data.model.MainTraining

class HistoryActivity : AppCompatActivity(), ItemClicks {

    private val adapter by lazy {
        HistoryAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setupRecycler()
    }

    private fun setupRecycler() {
        rvHistory.adapter = adapter

        FitnessApp.app?.getDB()?.getTraingDao()?.getTraingLiveData()?.observe(this, {
            adapter.submitList(it)
        })
    }

    override fun clickOnDelete(item: MainTraining) {
        GlobalScope.launch {
            kotlin.runCatching {
                FitnessApp.app?.getDB()?.getTraingDao()?.deleteTraing(item.id)
            }
        }
    }
}