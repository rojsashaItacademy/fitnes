package ru.trinitydigital.fitnes.ui

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.IBinder
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.*
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Geometry
import com.mapbox.geojson.Point
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import ru.trinitydigital.fitnes.data.NotificationHelper
import ru.trinitydigital.fitnes.data.events.TrainingEndedEvent
import ru.trinitydigital.fitnes.data.events.UserLocationEvent
import ru.trinitydigital.fitnes.utils.ForTest

class MyLocationForegroundService : Service() {

    private val job = Job()
    private val scope = CoroutineScope(job)

    var fusedLocation: FusedLocationProviderClient? = null

    private val list = arrayListOf<Point>()

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == STOP_SERVICE_ACTION) {
            stopForeground(true)
            stopSelf()
        } else {

            fusedLocation = LocationServices.getFusedLocationProviderClient(applicationContext)
            val locationRequest = LocationRequest()
            locationRequest.interval = 10_000
            locationRequest.fastestInterval = 3_000
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

            fusedLocation?.requestLocationUpdates(
                locationRequest,
                fLocListener,
                Looper.getMainLooper()
            )

            startForeground(1, NotificationHelper.createNotification(applicationContext))
            test()
        }
        return START_REDELIVER_INTENT
    }

    private val fLocListener = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult?.lastLocation?.let {
                list.add(Point.fromLngLat(it.longitude, it.latitude))

                EventBus.getDefault().post(UserLocationEvent(list))
            }
        }
    }

    fun test() {
        scope.launch(Dispatchers.Default) {
            kotlin.run {
                for (i in 0..10_000_000) {
                    delay(200)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocation?.removeLocationUpdates(fLocListener)
        scope.cancel()
        EventBus.getDefault().post(TrainingEndedEvent(true))
    }

    companion object {
        const val STOP_SERVICE_ACTION = "STOP_SERVICE_ACTION"
    }
}