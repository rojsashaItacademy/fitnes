package ru.trinitydigital.fitnes.ui.main

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import ru.trinitydigital.fitnes.R
import ru.trinitydigital.fitnes.base.BaseMapActivity
import ru.trinitydigital.fitnes.utils.showLongToast

class MainActivity : BaseMapActivity() {

    override fun getResId() = R.layout.activity_main
    override fun getMapViewId() = R.id.mapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        fab.setOnClickListener {
            showLongToast(R.string.app_name)
        }
    }
}