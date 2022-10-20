package io.homeland.companion.android.qs

import android.os.Build
import android.service.quicksettings.Tile
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.N)
class Tile6Service : TileExtensions() {

    companion object {
        const val TILE_ID = "tile_6"
    }

    override fun getTile(): Tile? {
        return if (qsTile != null)
            qsTile
        else
            null
    }

    override fun getTileId(): String {
        return TILE_ID
    }
}
