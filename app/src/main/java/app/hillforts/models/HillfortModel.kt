package app.hillforts.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HillfortModel(
        var fbId : String = "",
        var id: Long = 0,
        var userId: Long = 0,
        var title: String = "",
        var description: String = "",
        var notes: String = "",
        var dateVisited: String = "",
        var image: String = "",
        var lat: Double = 0.0,
        var lng: Double = 0.0,
        var zoom: Float = 0f): Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable