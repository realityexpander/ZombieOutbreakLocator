package com.realityexpander.zombieoutbreaklocator.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ZombieMarkerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertZombieMarker(zombieMarker: ZombieMarkerEntity)

    @Query("DELETE FROM parking_marker_entity WHERE id = :zombieMarkerId")
    suspend fun deleteZombieMarkerById(zombieMarkerId: Long)

    @Delete
    suspend fun deleteZombieMarker(zombieMarker: ZombieMarkerEntity)

    @Query("SELECT * FROM parking_marker_entity") // returns a flow, automatically updates when data changes
    fun getAllZombieMarkers(): Flow<List<ZombieMarkerEntity>> // flow is async by default
}