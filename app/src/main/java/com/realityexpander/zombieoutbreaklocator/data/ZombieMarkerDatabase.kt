package com.realityexpander.zombieoutbreaklocator.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ZombieMarkerEntity::class], version = 1)
abstract class ZombieMarkerDatabase: RoomDatabase() {
    abstract val zombieMarkerDao: ZombieMarkerDao
}