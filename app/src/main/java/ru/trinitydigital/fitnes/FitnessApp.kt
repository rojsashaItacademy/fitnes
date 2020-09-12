package ru.trinitydigital.fitnes

import android.app.Application
import ru.trinitydigital.fitnes.data.PreferenceHelper

class FitnessApp : Application() {

    override fun onCreate() {
        super.onCreate()
        PreferenceHelper.init(applicationContext)
    }
}