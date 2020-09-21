package ru.trinitydigital.fitnes.base

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Handler
import android.os.Looper
import com.mapbox.core.constants.Constants.PRECISION_6
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.Symbol
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import ru.trinitydigital.fitnes.R
import ru.trinitydigital.fitnes.utils.MapUtils
import ru.trinitydigital.fitnes.utils.PermissionUtils

abstract class BaseMapActivity : SupportMapActivity() {

    private var symbolManager: SymbolManager? = null
    private var symbol: Symbol? = null

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

//    private fun getDirections(latLng: LatLng) {
//        val location = map?.locationComponent?.lastKnownLocation
//
//        MapUtils.getDirections(location, latLng) { data ->
//            val source = map?.style?.getSourceAs<GeoJsonSource>(LINE_SOURCE)
//            source?.let { geoJsonSource ->
//                data?.geometry()?.let {
//                    source.setGeoJson(
//                        LineString.fromPolyline(
//                            it,
//                            PRECISION_6
//                        )
//                    )
//                }
//            }
//        }
//    }

    protected fun getDirections(latLng: ArrayList<Point>) {

        val source = map?.style?.getSourceAs<GeoJsonSource>(LINE_SOURCE)

        val lineString = LineString.fromLngLats(latLng) /////
        val featureCollection = FeatureCollection.fromFeature(Feature.fromGeometry(lineString))////


        source?.let { geoJsonSource ->
            geoJsonSource.setGeoJson(featureCollection)
        }
    }


    protected fun getDirections1(latLng: FeatureCollection) {

        val source = map?.style?.getSourceAs<GeoJsonSource>(LINE_SOURCE)

        source?.let { geoJsonSource ->
            geoJsonSource.setGeoJson(latLng)
        }
    }

    private fun setupListeners(mapBoxMap: MapboxMap) {
        mapBoxMap.addOnMapClickListener {
            addMarker(it)
//            getDirections(it)
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
            val locationComponent = map?.locationComponent
            locationComponent?.activateLocationComponent(
                LocationComponentActivationOptions.builder(applicationContext, it)
                    .build()
            )

            locationComponent?.isLocationComponentEnabled = true
            locationComponent?.cameraMode = CameraMode.TRACKING

            locationComponent?.renderMode = RenderMode.COMPASS

            val location = locationComponent?.lastKnownLocation

            val latLng = MapUtils.locationToLatLng(location)

            animateCamera(latLng)
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