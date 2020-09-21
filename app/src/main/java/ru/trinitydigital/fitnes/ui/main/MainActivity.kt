package ru.trinitydigital.fitnes.ui.main

import android.content.Intent
import android.os.Bundle
import com.mapbox.geojson.FeatureCollection
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import ru.trinitydigital.fitnes.R
import ru.trinitydigital.fitnes.base.BaseMapActivity
import ru.trinitydigital.fitnes.data.events.UserLocationEvent
import ru.trinitydigital.fitnes.ui.MyLocationForegroundService

class MainActivity : BaseMapActivity(), MainContract.View {

    override fun getResId() = R.layout.activity_main
    override fun getMapViewId() = R.id.mapView

    private var presenter: MainPresenter? = null

    private val intentService by lazy {
        val intent = Intent(this, MyLocationForegroundService::class.java)
        intent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupListeners()
        presenter = MainPresenter()
        presenter?.bind(this)
    }

    private fun setupListeners() {
        fab.setOnClickListener {
            startForegroundService()
        }
    }

    private fun startForegroundService() {
        startService(intentService)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun getUserData(event: UserLocationEvent) {
        presenter?.collectData(event.list)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onDestroy() {
        presenter?.unbind()
        super.onDestroy()
    }

    override fun showRoute(featureCollection: FeatureCollection) {
        getDirections(featureCollection)
    }
}