package com.realityexpander.zombieoutbreaklocator.api.googlemaps.geocode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoogleMapsGeocode(
    @SerialName("plus_code")
    val plusCode: PlusCode? = null,
    @SerialName("results")
    val results: List<Result>,
    @SerialName("status")
    val status: String
)