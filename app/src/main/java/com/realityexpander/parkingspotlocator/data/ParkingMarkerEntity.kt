package com.realityexpander.parkingspotlocator.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "parking_marker_entity")
data class ParkingMarkerEntity(
    @PrimaryKey val id: Long,
    val lat: Double?,
    val lng: Double?,
    val name: String,
    val address: String,
)

