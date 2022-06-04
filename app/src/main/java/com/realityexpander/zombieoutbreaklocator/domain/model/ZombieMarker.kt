package com.realityexpander.zombieoutbreaklocator.domain.model

class ZombieMarker (
    val id: Long = 0,
    val name: String = "",
    val address: String = "",
    val lat: Double? = null,
    val lng: Double? = null,
) {
    operator fun component1() = lat
    operator fun component2() = lng
}
