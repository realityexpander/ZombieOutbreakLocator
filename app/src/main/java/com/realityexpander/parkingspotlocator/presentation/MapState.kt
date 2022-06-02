package com.realityexpander.parkingspotlocator.presentation

import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.MapProperties

data class MapState(
    val mapProperties: MapProperties = MapProperties(),
    val parkingMarkers: List<ParkingMarker> = emptyList(),
    val isFalloutMapVisible: Boolean = false
)
