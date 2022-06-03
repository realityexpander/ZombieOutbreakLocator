package com.realityexpander.parkingspotlocator.data

import com.realityexpander.parkingspotlocator.domain.model.ParkingMarker
import com.realityexpander.parkingspotlocator.domain.repository.ParkingMarkerRepository
import com.realityexpander.parkingspotlocator.mappers.toParkingMarker
import com.realityexpander.parkingspotlocator.mappers.toParkingMarkerEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ParkingMarkerRepositoryImpl(
    private val parkingMarkerDao: ParkingMarkerDao
) : ParkingMarkerRepository {

    override suspend fun insertParkingMarker(parkingMarker: ParkingMarker) {
        parkingMarkerDao.insertParkingMarker(parkingMarker.toParkingMarkerEntity())
    }

    override suspend fun deleteParkingMarker(parkingMarker: ParkingMarker) {
        parkingMarkerDao.deleteParkingMarker(parkingMarker.toParkingMarkerEntity())
    }

    override suspend fun getParkingMarkers(): Flow<List<ParkingMarker>> {
        return parkingMarkerDao.getAllParkingMarkers().map { markers ->
            markers.map { parkingMarkerEntity ->
                parkingMarkerEntity.toParkingMarker()
            }
        }
    }
}