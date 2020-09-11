package ru.trinitydigital.fitnes.ui

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import ru.trinitydigital.fitnes.R

class MainActivity : BaseMapActivity() {

    override fun getResId() = R.layout.activity_main
    override fun getMapViewId() = R.id.mapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        fab.setOnClickListener {
        }
    }
}