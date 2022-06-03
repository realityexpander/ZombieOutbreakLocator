package com.realityexpander.parkingspotlocator.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.realityexpander.parkingspotlocator.data.ParkingMarkerDao
import com.realityexpander.parkingspotlocator.data.ParkingMarkerEntity

@Database(entities = [ParkingMarkerEntity::class], version = 1)
abstract class ParkingMarkerDatabase: RoomDatabase() {
    abstract val parkingMarkerDao: ParkingMarkerDao
}