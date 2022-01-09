package com.example.myassayment.maps

import android.icu.text.CaseMap
import com.example.myassayment.models.Doctor
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class ClusterMarker(
    var img:String,
    var doctor: Doctor,
) : ClusterItem {


    override fun getPosition(): LatLng {
        val loc=doctor.loc!!.split(",")
        val lat=loc[0].toDouble()
        val long=loc[1].toDouble()
        return LatLng(lat,long)
    }

    override fun getTitle(): String? {
        return "Dr:${doctor.nameEN}"
    }

    override fun getSnippet(): String? {
        return "Spetiality:${doctor.spicialty}"
    }
}