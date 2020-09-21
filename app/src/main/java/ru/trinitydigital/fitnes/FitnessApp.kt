package ru.trinitydigital.fitnes

import android.app.Application
import androidx.room.Room
import ru.trinitydigital.fitnes.data.local.PreferenceHelper
import ru.trinitydigital.fitnes.data.local.TrainingDataBase

class FitnessApp : Application() {

    private var db: TrainingDataBase? = null

    override fun onCreate() {
        super.onCreate()
        PreferenceHelper.init(applicationContext)
        app = this

        db = Room.databaseBuilder(
            applicationContext, TrainingDataBase::class.java, DB_Name
        )
            .build()
    }

    fun getDB() = db

    companion object {
        var app: FitnessApp? = null
        private const val DB_Name = "MY_DB"
    }
}