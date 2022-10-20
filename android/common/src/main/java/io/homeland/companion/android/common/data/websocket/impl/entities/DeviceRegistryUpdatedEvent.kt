package io.homeland.companion.android.common.data.websocket.impl.entities

data class DeviceRegistryUpdatedEvent(
    val action: String,
    val deviceId: String
)
