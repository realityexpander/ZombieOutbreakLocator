package com.realityexpander.zombieoutbreaklocator.presentation

import com.google.maps.android.compose.MapProperties
import com.realityexpander.zombieoutbreaklocator.domain.model.ZombieMarker

data class MapState(
    val mapProperties: MapProperties = MapProperties(),
    val zombieMarkers: List<ZombieMarker> = emptyList(),
    val isFalloutMapVisible: Boolean = true,
    val userMessages: List<UserMessage> = emptyList(),
)

data class UserMessage(val id: Long, val message: String)
