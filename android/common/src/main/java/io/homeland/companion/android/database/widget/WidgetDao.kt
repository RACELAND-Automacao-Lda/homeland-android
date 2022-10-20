package io.homeland.companion.android.database.widget

interface WidgetDao {
    suspend fun delete(id: Int)
}
