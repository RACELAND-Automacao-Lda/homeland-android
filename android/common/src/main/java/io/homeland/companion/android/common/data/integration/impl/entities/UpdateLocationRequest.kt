package io.homeland.companion.android.common.data.integration.impl.entities

data class UpdateLocationRequest(
    val gps: Array<Double>,
    val gpsAccuracy: Int,
    val speed: Int,
    val altitude: Int,
    val course: Int,
    val verticalAccuracy: Int
)
