package app.hillforts.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    var id: Long = 0,
    var email: String = "",
    var password: String = "",
    val hillforts: ArrayList<HillfortModel> = ArrayList()): Parcelable
