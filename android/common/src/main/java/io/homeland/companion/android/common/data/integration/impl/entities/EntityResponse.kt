package io.homeland.companion.android.common.data.integration.impl.entities

import java.util.Calendar

data class EntityResponse<T>(
    val entityId: String,
    val state: String,
    val attributes: T,
    val lastChanged: Calendar,
    val lastUpdated: Calendar,
    val context: Map<String, Any>
)
