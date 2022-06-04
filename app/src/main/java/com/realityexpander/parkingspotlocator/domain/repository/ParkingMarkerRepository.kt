package com.realityexpander.parkingspotlocator.domain.repository

import com.realityexpander.parkingspotlocator.domain.model.ParkingMarker
import kotlinx.coroutines.flow.Flow

interface ParkingMarkerRepository {
    suspend fun insertParkingMarker(parkingMarker: ParkingMarker)
    suspend fun deleteParkingMarker(parkingMarker: ParkingMarker)
    suspend fun deleteParkingMarkerById(parkingMarkerId: Long)
    suspend fun getParkingMarkers(): Flow<List<ParkingMarker>>
}