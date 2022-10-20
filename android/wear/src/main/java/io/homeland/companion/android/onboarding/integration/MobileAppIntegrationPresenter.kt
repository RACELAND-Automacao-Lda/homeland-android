package io.homeland.companion.android.onboarding.integration

interface MobileAppIntegrationPresenter {
    fun onRegistrationAttempt(deviceName: String)
    fun onFinish()
}
