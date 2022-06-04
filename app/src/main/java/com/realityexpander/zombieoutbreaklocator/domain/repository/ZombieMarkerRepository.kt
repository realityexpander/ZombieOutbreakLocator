package com.realityexpander.zombieoutbreaklocator.domain.repository

import com.realityexpander.zombieoutbreaklocator.domain.model.ZombieMarker
import kotlinx.coroutines.flow.Flow

interface ZombieMarkerRepository {
    suspend fun insertZombieMarker(zombieMarker: ZombieMarker)
    suspend fun deleteZombieMarker(zombieMarker: ZombieMarker)
    suspend fun deleteZombieMarkerById(zombieMarkerId: Long)
    suspend fun getZombieMarkers(): Flow<List<ZombieMarker>>
}