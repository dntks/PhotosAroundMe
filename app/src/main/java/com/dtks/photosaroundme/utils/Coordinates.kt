package com.dtks.photosaroundme.utils

data class Coordinates(
    val latitude: Double,
    val longitude: Double
){
    override fun toString(): String {
        return "$latitude° $longitude°"
    }
}
