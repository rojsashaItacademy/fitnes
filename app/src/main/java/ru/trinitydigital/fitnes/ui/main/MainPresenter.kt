package ru.trinitydigital.fitnes.ui.main

import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import kotlinx.coroutines.*
import ru.trinitydigital.fitnes.FitnessApp
import ru.trinitydigital.fitnes.data.model.LatLngPoints
import ru.trinitydigital.fitnes.data.model.MainTraining

class MainPresenter : MainContract.Presenter {

    private val scope = CoroutineScope(Job())

    private var view: MainContract.View? = null

    override fun collectData(list: ArrayList<Point>) {
        saveInDB(list)
        val lineString = LineString.fromLngLats(list)
        val featureCollection = FeatureCollection.fromFeature(Feature.fromGeometry(lineString))
        view?.showRoute(featureCollection)
    }

    override fun showLastRace() {
        scope.launch(Dispatchers.IO) {
            val data = FitnessApp.app?.getDB()?.getTraingDao()?.getTraing()
            data?.point?.points?.let { view?.showLastRoute(it) }
        }
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
            distance = 134,
            duration = 134,
            startAt = "asdasdasd",
            finishAt = "asdasdasd",
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