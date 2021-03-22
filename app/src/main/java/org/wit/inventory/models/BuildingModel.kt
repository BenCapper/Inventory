package org.wit.inventory.models
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BuildingModel(var id: Long = 0,
                         var name: String = "",
                         var address: String = "") : Parcelable {
}