package ru.trinitydigital.fitnes.ui.main

import android.util.Log
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import kotlinx.coroutines.*
import ru.trinitydigital.fitnes.FitnessApp
import ru.trinitydigital.fitnes.data.events.UserLocationEvent
import ru.trinitydigital.fitnes.data.model.LatLngPoints
import ru.trinitydigital.fitnes.data.model.MainTraining

class MainPresenter : MainContract.Presenter {

    private val scope = CoroutineScope(Job())

    private var view: MainContract.View? = null
    private var list = arrayListOf<Point>()
    private var distance: Double = 0.0
    private var startTime: Long = 0

    override fun collectData(list: ArrayList<Point>) {
        this.list = list
        val lineString = LineString.fromLngLats(list)
        val featureCollection = FeatureCollection.fromFeature(Feature.fromGeometry(lineString))
        view?.showRoute(featureCollection)
    }

    override fun collectDistance(distance: Double) {
        this.distance = distance
    }

    override fun showLastRace() {
        scope.launch(Dispatchers.IO) {
            val data = FitnessApp.app?.getDB()?.getTraingDao()?.getTraing()
            Log.d("adasdasd", "adasdasdsad")
//            data?.point?.points?.let { view?.showLastRoute(it) }
        }
    }

    override fun checkBSState(state: Int?) {
        if (state == BottomSheetBehavior.STATE_EXPANDED) {
            view?.changeBSState(BottomSheetBehavior.STATE_COLLAPSED)
        } else if (state == BottomSheetBehavior.STATE_COLLAPSED) {
            view?.changeBSState(BottomSheetBehavior.STATE_EXPANDED)
        }
    }

    override fun saveTraining() {
        saveInDB(list)
    }

    override fun saveCuurentTime() {
        startTime = System.currentTimeMillis()
    }

    private fun saveInDB(list: ArrayList<Point>) {
        scope.launch(Dispatchers.IO) {
            val data = getTrainingModel(list)
            FitnessApp.app?.getDB()?.getTraingDao()?.addTrainig(data)
        }
    }

    private fun getTrainingModel(list: ArrayList<Point>): MainTraining {
        return MainTraining(
            point = LatLngPoints(points = list),
            distance = distance,
            duration = System.currentTimeMillis() - startTime,
            startAt = startTime,
            finishAt = System.currentTimeMillis(),
            calories = 124
        )
    }

    override fun bind(view: MainContract.View) {
        this.view = view
    }

    override fun unbind() {
        scope.cancel()
        view = null
    }
}