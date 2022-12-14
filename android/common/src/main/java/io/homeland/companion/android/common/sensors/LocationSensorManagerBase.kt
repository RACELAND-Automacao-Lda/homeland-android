package io.homeland.companion.android.common.sensors

import android.content.BroadcastReceiver
import io.homeland.companion.android.common.data.integration.IntegrationRepository
import javax.inject.Inject

abstract class LocationSensorManagerBase : BroadcastReceiver(), SensorManager {
    @Inject
    lateinit var integrationUseCase: IntegrationRepository
}
