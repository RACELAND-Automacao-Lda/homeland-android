package io.homeland.companion.android.common.data

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import androidx.core.content.getSystemService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.homeland.companion.android.common.LocalStorageImpl
import io.homeland.companion.android.common.data.authentication.AuthenticationRepository
import io.homeland.companion.android.common.data.authentication.impl.AuthenticationRepositoryImpl
import io.homeland.companion.android.common.data.authentication.impl.AuthenticationService
import io.homeland.companion.android.common.data.integration.IntegrationRepository
import io.homeland.companion.android.common.data.integration.impl.IntegrationRepositoryImpl
import io.homeland.companion.android.common.data.integration.impl.IntegrationService
import io.homeland.companion.android.common.data.keychain.KeyChainRepository
import io.homeland.companion.android.common.data.keychain.KeyChainRepositoryImpl
import io.homeland.companion.android.common.data.prefs.PrefsRepository
import io.homeland.companion.android.common.data.prefs.PrefsRepositoryImpl
import io.homeland.companion.android.common.data.url.UrlRepository
import io.homeland.companion.android.common.data.url.UrlRepositoryImpl
import io.homeland.companion.android.common.data.websocket.WebSocketRepository
import io.homeland.companion.android.common.data.websocket.impl.WebSocketRepositoryImpl
import io.homeland.companion.android.common.data.wifi.WifiHelper
import io.homeland.companion.android.common.data.wifi.WifiHelperImpl
import okhttp3.OkHttpClient
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    companion object {
        @Provides
        @Singleton
        fun provideAuthenticationService(homeLandApis: HomeLandApis): AuthenticationService =
            homeLandApis.retrofit.create(AuthenticationService::class.java)

        @Provides
        @Singleton
        fun providesIntegrationService(homeLandApis: HomeLandApis): IntegrationService =
            homeLandApis.retrofit.create(IntegrationService::class.java)

        @Provides
        @Singleton
        fun providesOkHttpClient(homeLandApis: HomeLandApis): OkHttpClient =
            homeLandApis.okHttpClient

        @Provides
        @Named("url")
        @Singleton
        fun provideUrlLocalStorage(@ApplicationContext appContext: Context): LocalStorage =
            LocalStorageImpl(
                appContext.getSharedPreferences(
                    "url_0",
                    Context.MODE_PRIVATE
                )
            )

        @Provides
        @Named("session")
        @Singleton
        fun provideSessionLocalStorage(@ApplicationContext appContext: Context): LocalStorage =
            LocalStorageImpl(
                appContext.getSharedPreferences(
                    "session_0",
                    Context.MODE_PRIVATE
                )
            )

        @Provides
        @Named("integration")
        @Singleton
        fun provideIntegrationLocalStorage(@ApplicationContext appContext: Context): LocalStorage =
            LocalStorageImpl(
                appContext.getSharedPreferences(
                    "integration_0",
                    Context.MODE_PRIVATE
                )
            )

        @Provides
        @Named("themes")
        @Singleton
        fun providePrefsLocalStorage(@ApplicationContext appContext: Context): LocalStorage =
            LocalStorageImpl(
                appContext.getSharedPreferences(
                    "themes_0",
                    Context.MODE_PRIVATE
                )
            )

        @Provides
        @Named("manufacturer")
        @Singleton
        fun provideDeviceManufacturer(): String = Build.MANUFACTURER

        @Provides
        @Named("model")
        @Singleton
        fun provideDeviceModel(): String = Build.MODEL

        @Provides
        @Named("osVersion")
        @Singleton
        fun provideDeviceOsVersion() = Build.VERSION.SDK_INT.toString()

        @SuppressLint("HardwareIds")
        @Provides
        @Named("deviceId")
        @Singleton
        fun provideDeviceId(@ApplicationContext appContext: Context) = Settings.Secure.getString(
            appContext.contentResolver,
            Settings.Secure.ANDROID_ID
        )

        @Provides
        @Singleton
        fun wifiManager(@ApplicationContext appContext: Context) = appContext.getSystemService<WifiManager>()!!
    }

    @Binds
    @Singleton
    abstract fun bindAuthRepository(authenticationRepository: AuthenticationRepositoryImpl): AuthenticationRepository

    @Binds
    @Singleton
    abstract fun bindIntegrationRepository(integrationRepository: IntegrationRepositoryImpl): IntegrationRepository

    @Binds
    @Singleton
    abstract fun bindPrefsRepository(prefsRepository: PrefsRepositoryImpl): PrefsRepository

    @Binds
    @Singleton
    abstract fun bindUrlRepository(urlRepository: UrlRepositoryImpl): UrlRepository

    @Binds
    @Singleton
    abstract fun bindWebSocketRepository(webSocketRepository: WebSocketRepositoryImpl): WebSocketRepository

    @Binds
    @Singleton
    abstract fun bindWifiRepository(wifiHelper: WifiHelperImpl): WifiHelper

    @Binds
    @Singleton
    abstract fun bindKeyChainRepository(keyChainRepository: KeyChainRepositoryImpl): KeyChainRepository
}
