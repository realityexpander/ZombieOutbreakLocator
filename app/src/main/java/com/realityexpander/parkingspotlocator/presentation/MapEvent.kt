package com.realityexpander.parkingspotlocator.presentation

import com.google.android.gms.maps.model.LatLng

sealed class MapEvent {
    object ToggleFalloutMap : MapEvent()
    data class MapLongClick(val latLng: LatLng) : MapEvent()
}
