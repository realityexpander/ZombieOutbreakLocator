package com.realityexpander.parkingspotlocator.mappers

import com.realityexpander.parkingspotlocator.data.ParkingMarkerEntity
import com.realityexpander.parkingspotlocator.domain.model.ParkingMarker

fun ParkingMarker.toParkingMarkerEntity(): ParkingMarkerEntity {
    return ParkingMarkerEntity(
        id = id,
        name = name,
        address = address,
        lat = lat,
        lng = lng,
    )
}

fun ParkingMarkerEntity.toParkingMarker(): ParkingMarker {
    return ParkingMarker(
        id = id,
        name = name,
        address = address,
        lat = lat,
        lng = lng,
    )
}

fun List<ParkingMarker>.toParkingMarkerEntityList(): List<ParkingMarkerEntity> {
    return map { it.toParkingMarkerEntity() }
}

fun List<ParkingMarkerEntity>.toParkingMarkerList(): List<ParkingMarker> {
    return map { it.toParkingMarker() }
}