package ru.trinitydigital.fitnes.data.events

import android.location.Location
import com.mapbox.geojson.Point

data class UserLocationEvent(
    val list: ArrayList<Point>,
    val distance: Double
)

data class TrainingEndedEvent(
    val isFinished: Boolean
)