package ru.trinitydigital.fitnes.ui.main

import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import ru.trinitydigital.fitnes.ui.LiveCycle

interface MainContract {

    interface View {
        fun showRoute(featureCollection: FeatureCollection)
    }

    interface Presenter : LiveCycle<View> {
        fun collectData(list: ArrayList<Point>)
    }
}