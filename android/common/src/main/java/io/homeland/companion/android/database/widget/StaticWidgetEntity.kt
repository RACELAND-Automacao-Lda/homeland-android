package io.homeland.companion.android.database.widget

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "static_widget")
data class StaticWidgetEntity(
    @PrimaryKey
    override val id: Int,
    @ColumnInfo(name = "entity_id")
    val entityId: String,
    @ColumnInfo(name = "attribute_ids")
    val attributeIds: String?,
    @ColumnInfo(name = "label")
    val label: String?,
    @ColumnInfo(name = "text_size")
    val textSize: Float = 30F,
    @ColumnInfo(name = "state_separator")
    val stateSeparator: String = "",
    @ColumnInfo(name = "attribute_separator")
    val attributeSeparator: String = "",
    @ColumnInfo(name = "last_update")
    val lastUpdate: String,
    @ColumnInfo(name = "background_type", defaultValue = "DAYNIGHT")
    override val backgroundType: WidgetBackgroundType = WidgetBackgroundType.DAYNIGHT,
    @ColumnInfo(name = "text_color")
    override val textColor: String? = null
) : WidgetEntity, ThemeableWidgetEntity
