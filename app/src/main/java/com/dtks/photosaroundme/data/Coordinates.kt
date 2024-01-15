package com.dtks.photosaroundme.data

data class Coordinates(
    val latitude: Double,
    val longitude: Double
){
    override fun toString(): String {
        return "$latitude° $longitude°"
    }
}
