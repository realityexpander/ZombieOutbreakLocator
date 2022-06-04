package com.realityexpander.parkingspotlocator.presentation

import com.google.android.gms.maps.model.LatLng

sealed class MapEvent {
    object OnToggleFalloutMap : MapEvent()
    data class OnMapLongClick(val latLng: LatLng) : MapEvent()
    data class OnRemoveUserMessage(val userMessageId: Long) : MapEvent()
}
