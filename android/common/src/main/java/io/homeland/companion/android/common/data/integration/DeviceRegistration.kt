package io.homeland.companion.android.common.data.integration

data class DeviceRegistration(
    val appVersion: String? = null,
    val deviceName: String? = null,
    var pushToken: String? = null
)
