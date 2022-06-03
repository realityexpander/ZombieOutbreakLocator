package com.realityexpander.parkingspotlocator.presentation

import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.MapProperties
import java.util.*

data class MapState(
    val mapProperties: MapProperties = MapProperties(),
    val parkingMarkers: List<ParkingMarker> = emptyList(),
    val isFalloutMapVisible: Boolean = false,
    val userMessages: List<UserMessage> = emptyList(),
)

data class UserMessage(val id: Long, val message: String)
