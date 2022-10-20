package io.homeland.companion.android.onboarding

import android.app.Application
import android.webkit.URLUtil
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.getSystemService
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.homeland.companion.android.common.R
import io.homeland.companion.android.common.data.authentication.AuthenticationRepository
import io.homeland.companion.android.database.sensor.SensorDao
import io.homeland.companion.android.onboarding.discovery.HomeLandInstance
import io.homeland.companion.android.onboarding.discovery.HomeLandSearcher
import io.homeland.companion.android.sensors.LocationSensorManager
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    app: Application,
    val authenticationRepository: AuthenticationRepository,
    private val sensorDao: SensorDao
) : AndroidViewModel(app) {

    private val _homeLandSearcher = HomeLandSearcher(
        nsdManager = app.getSystemService()!!,
        wifiManager = app.getSystemService(),
        onInstanceFound = { instance ->
            if (foundInstances.none { it.url == instance.url }) {
                foundInstances.add(instance)
            }
        },
        onError = {
            Toast.makeText(app, R.string.failed_scan, Toast.LENGTH_LONG).show()
            // TODO: Go to manual setup?
        }
    )
    val homeLandSearcher: LifecycleObserver = _homeLandSearcher

    val foundInstances = mutableStateListOf<HomeLandInstance>()
    val manualUrl = mutableStateOf("")
    var manualContinueEnabled by mutableStateOf(false)
        private set
    var authCode by mutableStateOf("")
        private set
    val deviceName = mutableStateOf("")
    val locationTrackingPossible = mutableStateOf(false)
    var locationTrackingEnabled by mutableStateOf(false)

    fun onManualUrlUpdated(url: String) {
        manualUrl.value = url
        manualContinueEnabled = URLUtil.isValidUrl(url)
    }

    fun registerAuthCode(code: String) {
        authCode = code
    }

    fun onDeviceNameUpdated(name: String) {
        deviceName.value = name
    }

    fun setLocationTracking(enabled: Boolean) {
        viewModelScope.launch {
            sensorDao.setSensorsEnabled(
                sensorIds = listOf(
                    LocationSensorManager.backgroundLocation.id,
                    LocationSensorManager.zoneLocation.id,
                    LocationSensorManager.singleAccurateLocation.id
                ),
                enabled = enabled
            )
        }
        locationTrackingEnabled = enabled
    }

    override fun onCleared() {
        _homeLandSearcher.stopSearch()
    }
}
