package ru.trinitydigital.fitnes.base

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mapbox.geojson.FeatureCollection
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.LocationComponentOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.Symbol
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import ru.trinitydigital.fitnes.R
import ru.trinitydigital.fitnes.utils.Constants.MOSCOW
import ru.trinitydigital.fitnes.utils.MapUtils
import ru.trinitydigital.fitnes.utils.PermissionUtils

abstract class BaseMapActivity : SupportMapActivity() {

    private var symbolManager: SymbolManager? = null
    private var symbol: Symbol? = null

    private var fusedLocation: FusedLocationProviderClient? = null

    override fun onMapLoaded(mapBoxMap: MapboxMap, style: Style) {
        setupListeners(mapBoxMap)
        loadImages(style)
        initSource(style)
        initLayer(style)
        mapView?.let { symbolManager = SymbolManager(it, mapBoxMap, style) }
        if (PermissionUtils.requestLocationPermission(this))
            showUserLocation()
    }

    private fun initLayer(style: Style) {
        val layer = MapUtils.createLayer(
            layerName = LINE_LAYER,
            sourceName = LINE_SOURCE
        )
        style.addLayer(layer)
    }

    private fun initSource(style: Style) {
        style.addSource(MapUtils.getSource(LINE_SOURCE))
    }

    protected fun getDirections(featureCollection: FeatureCollection) {
        val source = map?.style?.getSourceAs<GeoJsonSource>(LINE_SOURCE)
        source?.let { geoJsonSource ->
            geoJsonSource.setGeoJson(featureCollection)
        }
    }

    private fun setupListeners(mapBoxMap: MapboxMap) {
        mapBoxMap.addOnMapClickListener {
            addMarker(it)
            return@addOnMapClickListener true
        }
    }

    private fun loadImages(style: Style) {
        MapUtils.addImage(
            style,
            MARKER_IMAGE,
            resources.getDrawable(R.drawable.ic_baseline_add_location_24)
        )
    }

    private fun addMarker(latLng: LatLng) {
        symbol?.let { symbolManager?.delete(it) }
        val symbolOptions = MapUtils.createSymbol(latLng, MARKER_IMAGE)
        symbol = symbolManager?.create(symbolOptions)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PermissionUtils.LOCATION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showUserLocation()
            }
        }
    }

    @SuppressLint("MissingPermission", "Range")
    private fun showUserLocation() {
        map?.style?.let {
            fusedLocation = LocationServices.getFusedLocationProviderClient(this)
            val locationComponentOptions = LocationComponentOptions.builder(this)
                .build()

            val locationComponentActivationOptions = LocationComponentActivationOptions
                .builder(this, it)
                .locationComponentOptions(locationComponentOptions)
                .build()

            var locationComponent = map?.locationComponent
            locationComponent?.activateLocationComponent(
                LocationComponentActivationOptions.builder(applicationContext, it)
                    .build()
            )

            locationComponent?.activateLocationComponent(locationComponentActivationOptions)

            locationComponent?.isLocationComponentEnabled = true
            locationComponent?.cameraMode = CameraMode.TRACKING

            locationComponent?.renderMode = RenderMode.COMPASS

            fusedLocation?.lastLocation?.addOnSuccessListener {
                if (it == null) {
                    animateCamera(MOSCOW)
                } else {
                    animateCamera(LatLng(it.latitude, it.longitude))
                }
            }
        }
    }

    private fun animateCamera(latLng: LatLng, zoom: Double = CAMERA_ZOOM) {
        Handler(Looper.getMainLooper()).postDelayed({
            map?.animateCamera(
                MapUtils.getCameraPosition(latLng, zoom),
                CAMERA_DURATION
            )
        }, 1000)
    }

    companion object {
        private const val MARKER_IMAGE = "MARKER_IMAGE"
        private const val LINE_SOURCE = "LINE_SOURCE"
        private const val LINE_LAYER = "LINE_LAYER"

        private const val CAMERA_DURATION = 6000
        private const val CAMERA_ZOOM = 17.0
    }
}