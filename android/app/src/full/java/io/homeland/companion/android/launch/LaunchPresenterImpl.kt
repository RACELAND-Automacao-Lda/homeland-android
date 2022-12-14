package io.homeland.companion.android.launch

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import io.homeland.companion.android.BuildConfig
import io.homeland.companion.android.common.data.authentication.AuthenticationRepository
import io.homeland.companion.android.common.data.integration.DeviceRegistration
import io.homeland.companion.android.common.data.integration.IntegrationRepository
import io.homeland.companion.android.onboarding.getMessagingToken
import kotlinx.coroutines.launch
import javax.inject.Inject

@ActivityScoped
class LaunchPresenterImpl @Inject constructor(
    @ActivityContext context: Context,
    authenticationUseCase: AuthenticationRepository,
    integrationUseCase: IntegrationRepository
) : LaunchPresenterBase(context as LaunchView, authenticationUseCase, integrationUseCase) {
    override fun resyncRegistration() {
        ioScope.launch {
            try {
                integrationUseCase.updateRegistration(
                    DeviceRegistration(
                        "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})",
                        null,
                        getMessagingToken()
                    )
                )
            } catch (e: Exception) {
                Log.e(TAG, "Issue updating Registration", e)
            }
        }
    }
}
