package ru.trinitydigital.fitnes.data.local

import android.text.TextUtils
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mapbox.geojson.Point
import ru.trinitydigital.fitnes.data.model.LatLngPoints

object TrainingTypeConverter {

    //Object
    @JvmStatic
    @TypeConverter
    fun coordToString(model: LatLngPoints): String {
        return Gson().toJson(model)
    }

    @JvmStatic
    @TypeConverter
    fun coordToObject(text: String): LatLngPoints? {
        if (TextUtils.isEmpty(text))
            return null
        return Gson().fromJson(text, LatLngPoints::class.java)
    }

    @JvmStatic
    @TypeConverter
    fun weatherToString(model: List<Point>): String {
        return Gson().toJson(model)
    }

    @JvmStatic
    @TypeConverter
    fun weatherToObject(text: String?): List<Point>? {
        if (text == null) return mutableListOf()
        val typeToken = object : TypeToken<List<Point>>() {}.type
        return Gson().fromJson(text, typeToken)
    }
}