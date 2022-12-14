package io.homeland.companion.android.common.data.websocket.impl.entities

data class EntityRegistryUpdatedEvent(
    val action: String,
    val entityId: String,
    val changes: Map<String, Any?>?,
    val oldEntityId: String?
)
