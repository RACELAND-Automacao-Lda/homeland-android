package io.homeland.companion.android.sensors

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import io.homeland.companion.android.BuildConfig
import io.homeland.companion.android.common.sensors.BatterySensorManager
import io.homeland.companion.android.common.sensors.SensorManager
import io.homeland.companion.android.common.sensors.SensorReceiverBase

@AndroidEntryPoint
class SensorReceiver : SensorReceiverBase() {

    override val tag: String
        get() = TAG

    override val currentAppVersion: String
        get() = BuildConfig.VERSION_NAME

    override val managers: List<SensorManager>
        get() = MANAGERS

    companion object {
        const val TAG = "SensorReceiver"
        val MANAGERS = listOf(
            BatterySensorManager()
        )

        const val ACTION_REQUEST_SENSORS_UPDATE =
            "io.homeland.companion.android.background.REQUEST_SENSORS_UPDATE"
    }

    // Suppress Lint because we only register for the receiver if the android version matches the intent
    @SuppressLint("InlinedApi")
    override val skippableActions = mapOf<String, String>()

    override fun getSensorSettingsIntent(context: Context, id: String): Intent? = null
}
