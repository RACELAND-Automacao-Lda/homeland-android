package io.homeland.companion.android.onboarding

import androidx.annotation.StringRes

interface OnboardingView {
    fun startIntegration()

    fun onInstanceFound(instance: HomeLandInstance)
    fun onInstanceLost(instance: HomeLandInstance)

    fun showLoading()

    fun showContinueOnPhone()

    fun showError(@StringRes message: Int? = null)
}
