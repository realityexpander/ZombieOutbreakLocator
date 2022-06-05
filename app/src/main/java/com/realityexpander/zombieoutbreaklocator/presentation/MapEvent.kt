package com.realityexpander.zombieoutbreaklocator.presentation

import com.google.android.gms.maps.model.LatLng

sealed class MapEvent {
    object OnToggleMapStyle : MapEvent()
    data class OnMapLongClick(val latLng: LatLng) : MapEvent()
    data class OnRemoveUserMessage(val userMessageId: Long) : MapEvent()
    data class OnInfoWindowLongClick(val zombieMarkerId: Long) : MapEvent()
}
