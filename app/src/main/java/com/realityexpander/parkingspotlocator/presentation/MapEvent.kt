package com.realityexpander.parkingspotlocator.presentation

import com.google.android.gms.maps.model.LatLng
import java.util.*

sealed class MapEvent {
    object ToggleFalloutMap : MapEvent()
    data class MapLongClick(val latLng: LatLng) : MapEvent()
    data class ClearUserMessage(val userMessageId: Long) : MapEvent()
}
