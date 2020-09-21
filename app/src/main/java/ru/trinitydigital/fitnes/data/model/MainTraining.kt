package ru.trinitydigital.fitnes.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MainTraining(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val point: LatLngPoints,
    val distance: Int,
    val duration: Int,
    val startAt: String,
    val finishAt: String,
    val calories: Int

)