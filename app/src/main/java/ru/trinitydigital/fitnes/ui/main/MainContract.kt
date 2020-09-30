package ru.trinitydigital.fitnes.ui.main

import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import ru.trinitydigital.fitnes.data.events.UserLocationEvent
import ru.trinitydigital.fitnes.ui.LiveCycle

interface MainContract {

    interface View {
        fun showRoute(featureCollection: FeatureCollection)
        fun showLastRoute(points: ArrayList<Point>)
        fun changeBSState(stateCollapsed: Int)
    }

    interface Presenter : LiveCycle<View> {
        fun collectData(list: ArrayList<Point>)
        fun collectDistance(distance: Double)
        fun showLastRace()
        fun checkBSState(state: Int?)
        fun saveTraining()
        fun saveCuurentTime()

    }
}