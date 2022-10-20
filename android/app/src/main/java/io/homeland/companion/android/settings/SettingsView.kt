package io.homeland.companion.android.settings

interface SettingsView {

    fun disableInternalConnection()

    fun enableInternalConnection()

    fun updateSsids(ssids: Set<String>)

    fun onLangSettingsChanged()
}
