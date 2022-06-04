package com.realityexpander.zombieoutbreaklocator.mappers

import com.realityexpander.zombieoutbreaklocator.data.ZombieMarkerEntity
import com.realityexpander.zombieoutbreaklocator.domain.model.ZombieMarker

fun ZombieMarker.toZombieMarkerEntity(): ZombieMarkerEntity {
    return ZombieMarkerEntity(
        id = id,
        name = name,
        address = address,
        lat = lat,
        lng = lng,
    )
}

fun ZombieMarkerEntity.toZombieMarker(): ZombieMarker {
    return ZombieMarker(
        id = id,
        name = name,
        address = address,
        lat = lat,
        lng = lng,
    )
}

fun List<ZombieMarker>.toZombieMarkerEntityList(): List<ZombieMarkerEntity> {
    return map { it.toZombieMarkerEntity() }
}

fun List<ZombieMarkerEntity>.toZombieMarkerList(): List<ZombieMarker> {
    return map { it.toZombieMarker() }
}