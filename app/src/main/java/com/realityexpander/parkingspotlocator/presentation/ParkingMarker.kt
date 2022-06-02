package com.realityexpander.parkingspotlocator.presentation

data class ParkingMarker (
    val id: String,
    val name: String,
    val address: String,
    val lat: Double,
    val lng: Double,
)
