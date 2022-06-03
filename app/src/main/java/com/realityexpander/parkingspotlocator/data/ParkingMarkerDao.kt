package com.realityexpander.parkingspotlocator.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ParkingMarkerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParkingMarker(parkingMarker: ParkingMarkerEntity)

    @Delete
    suspend fun deleteParkingMarker(parkingMarker: ParkingMarkerEntity)

    @Query("SELECT * FROM parking_marker_entity") // returns a flow, automatically updates when data changes
    fun getAllParkingMarkers(): Flow<List<ParkingMarkerEntity>> // flow is async by default
}