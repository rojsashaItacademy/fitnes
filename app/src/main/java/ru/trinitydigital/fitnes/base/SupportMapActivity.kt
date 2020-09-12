package ru.trinitydigital.fitnes.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import ru.trinitydigital.fitnes.BuildConfig

abstract class SupportMapActivity : AppCompatActivity() {

    abstract fun getResId(): Int
    abstract fun getMapViewId(): Int
    abstract fun onMapLoaded(mapBoxMap: MapboxMap, style: Style)

    protected var mapView: MapView? = null
    protected var map: MapboxMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(applicationContext, BuildConfig.API_KEY_MAPS)
        setContentView(getResId())
        mapView = findViewById(getMapViewId())
        mapView?.onCreate(savedInstanceState)

        mapView?.getMapAsync { mapBoxMap ->
            map = mapBoxMap
            mapBoxMap.setStyle(Style.LIGHT) { style ->
                onMapLoaded(mapBoxMap, style)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }
}