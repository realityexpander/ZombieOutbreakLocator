package com.realityexpander.zombieoutbreaklocator.data

import com.realityexpander.zombieoutbreaklocator.domain.model.ZombieMarker
import com.realityexpander.zombieoutbreaklocator.domain.repository.ZombieMarkerRepository
import com.realityexpander.zombieoutbreaklocator.mappers.toZombieMarker
import com.realityexpander.zombieoutbreaklocator.mappers.toZombieMarkerEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ZombieMarkerRepositoryImpl(
    private val zombieMarkerDao: ZombieMarkerDao
) : ZombieMarkerRepository {

    override suspend fun insertZombieMarker(zombieMarker: ZombieMarker) {
        zombieMarkerDao.insertZombieMarker(zombieMarker.toZombieMarkerEntity())
    }

    override suspend fun deleteZombieMarker(zombieMarker: ZombieMarker) {
        zombieMarkerDao.deleteZombieMarker(zombieMarker.toZombieMarkerEntity())
    }

    override suspend fun deleteZombieMarkerById(zombieMarkerId: Long) {
        zombieMarkerDao.deleteZombieMarkerById(zombieMarkerId)
    }

    override suspend fun getZombieMarkers(): Flow<List<ZombieMarker>> {
        return zombieMarkerDao.getAllZombieMarkers().map { markers ->
            markers.map { zombieMarkerEntity ->
                zombieMarkerEntity.toZombieMarker()
            }
        }
    }
}