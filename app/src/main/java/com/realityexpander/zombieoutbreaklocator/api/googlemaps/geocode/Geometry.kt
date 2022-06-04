package com.realityexpander.zombieoutbreaklocator.api.googlemaps.geocode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Geometry(
    @SerialName("bounds")
    val bounds: Bounds? = null,
    @SerialName("location")
    val location: Location,
    @SerialName("location_type")
    val locationType: String,
    @SerialName("viewport")
    val viewport: Viewport
)