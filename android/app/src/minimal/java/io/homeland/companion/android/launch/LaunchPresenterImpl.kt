package io.homeland.companion.android.launch

import android.util.Log
import io.homeland.companion.android.BuildConfig
import io.homeland.companion.android.common.data.authentication.AuthenticationRepository
import io.homeland.companion.android.common.data.integration.DeviceRegistration
import io.homeland.companion.android.common.data.integration.IntegrationRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class LaunchPresenterImpl @Inject constructor(
    view: LaunchView,
    authenticationUseCase: AuthenticationRepository,
    integrationUseCase: IntegrationRepository
) : LaunchPresenterBase(view, authenticationUseCase, integrationUseCase) {
    override fun resyncRegistration() {
        mainScope.launch {
            try {
                integrationUseCase.updateRegistration(
                    DeviceRegistration(
                        "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
                    )
                )
            } catch (e: Exception) {
                Log.e(TAG, "Issue updating Registration", e)
            }
        }
    }
}
