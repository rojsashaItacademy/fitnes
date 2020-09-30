package ru.trinitydigital.fitnes.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_bottom_sheet.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import ru.trinitydigital.fitnes.R
import ru.trinitydigital.fitnes.base.BaseMapActivity
import ru.trinitydigital.fitnes.data.events.TrainingEndedEvent
import ru.trinitydigital.fitnes.data.events.UserLocationEvent
import ru.trinitydigital.fitnes.ui.MyLocationForegroundService
import ru.trinitydigital.fitnes.ui.TestBottomSheet
import ru.trinitydigital.fitnes.ui.history.HistoryActivity

class MainActivity : BaseMapActivity(), MainContract.View {

    override fun getResId() = R.layout.activity_main
    override fun getMapViewId() = R.id.mapView

    private var presenter: MainPresenter? = null

    private lateinit var bottomSheetBehaviour: BottomSheetBehavior<ConstraintLayout>

    private val intentService by lazy {
        val intent = Intent(this, MyLocationForegroundService::class.java)
        intent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBottomSheet()
        setupListeners()
        presenter = MainPresenter()
        presenter?.bind(this)
        presenter?.showLastRace()
    }

    private fun setupListeners() {
        fab.setOnClickListener {
            startForegroundService()
//            val bottomSheet = TestBottomSheet()
//            bottomSheet.show(supportFragmentManager, "test")
        }

        btnAllTrainings.setOnClickListener {
            startActivity(Intent(applicationContext, HistoryActivity::class.java))
        }

        btnBottomSheet.setOnClickListener {
            presenter?.checkBSState(bottomSheetBehaviour.state)
        }


    }

    private fun setupBottomSheet() {
        bottomSheetBehaviour = BottomSheetBehavior.from(bottomSheet)

        bottomSheetBehaviour.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {

            override fun onStateChanged(bottomSheet: View, newState: Int) {}

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Log.d("asdasdasdasd", "Asdadasdasdasd")
            }
        })
    }

    private fun startForegroundService() {
        startService(intentService)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun getUserData(event: UserLocationEvent) {
        presenter?.collectData(event.list)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun endedTraining(event: TrainingEndedEvent) {
        presenter?.saveTraining()
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
        runOnUiThread { getDirections(featureCollection) }
    }

    override fun showLastRoute(points: ArrayList<Point>) {
        presenter?.collectData(points)
    }

    override fun changeBSState(stateCollapsed: Int) {
        bottomSheetBehaviour.state = stateCollapsed
    }
}