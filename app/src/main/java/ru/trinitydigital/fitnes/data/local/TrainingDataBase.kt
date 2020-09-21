package ru.trinitydigital.fitnes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.trinitydigital.fitnes.data.model.LatLngPoints
import ru.trinitydigital.fitnes.data.model.MainTraining

@TypeConverters(value = [TrainingTypeConverter::class])
@Database(entities = [MainTraining::class, LatLngPoints::class], version = 1)
abstract class TrainingDataBase : RoomDatabase() {
}