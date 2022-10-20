package io.homeland.companion.android.common.data.integration.impl.entities

data class ServiceCallRequest(
    val domain: String,
    val service: String,
    val serviceData: HashMap<String, Any>
)
