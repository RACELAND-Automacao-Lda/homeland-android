package io.homeland.companion.android.launch

interface LaunchView {
    fun displayWebview()

    fun displayOnBoarding(sessionConnected: Boolean)
}
