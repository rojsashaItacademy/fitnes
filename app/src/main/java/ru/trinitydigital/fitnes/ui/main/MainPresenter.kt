package ru.trinitydigital.fitnes.ui.main

import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point

class MainPresenter : MainContract.Presenter {

    private var view: MainContract.View? = null

    override fun collectData(list: ArrayList<Point>) {
        val lineString = LineString.fromLngLats(list)
        val featureCollection = FeatureCollection.fromFeature(Feature.fromGeometry(lineString))
        view?.showRoute(featureCollection)
    }

    override fun bind(view: MainContract.View) {
        this.view = view
    }

    override fun unbind() {
        view = null
    }
}