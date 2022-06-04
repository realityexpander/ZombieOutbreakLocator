package com.realityexpander.zombieoutbreaklocator.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "parking_marker_entity")
data class ZombieMarkerEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val lat: Double?,
    val lng: Double?,
    val name: String,
    val address: String,
)

