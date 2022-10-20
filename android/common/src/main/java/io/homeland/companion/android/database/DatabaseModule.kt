package io.homeland.companion.android.database

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.homeland.companion.android.database.authentication.AuthenticationDao
import io.homeland.companion.android.database.notification.NotificationDao
import io.homeland.companion.android.database.qs.TileDao
import io.homeland.companion.android.database.sensor.SensorDao
import io.homeland.companion.android.database.settings.SettingsDao
import io.homeland.companion.android.database.wear.EntityStateComplicationsDao
import io.homeland.companion.android.database.wear.FavoritesDao
import io.homeland.companion.android.database.widget.ButtonWidgetDao
import io.homeland.companion.android.database.widget.CameraWidgetDao
import io.homeland.companion.android.database.widget.MediaPlayerControlsWidgetDao
import io.homeland.companion.android.database.widget.StaticWidgetDao
import io.homeland.companion.android.database.widget.TemplateWidgetDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getInstance(context)

    @Provides
    fun provideAuthenticationDao(database: AppDatabase): AuthenticationDao =
        database.authenticationDao()

    @Provides
    fun provideSensorDao(database: AppDatabase): SensorDao = database.sensorDao()

    @Provides
    fun provideButtonWidgetDao(database: AppDatabase): ButtonWidgetDao = database.buttonWidgetDao()

    @Provides
    fun provideCameraWidgetDao(database: AppDatabase): CameraWidgetDao = database.cameraWidgetDao()

    @Provides
    fun provideMediaPlayCtrlWidgetDao(database: AppDatabase): MediaPlayerControlsWidgetDao =
        database.mediaPlayCtrlWidgetDao()

    @Provides
    fun provideStaticWidgetDao(database: AppDatabase): StaticWidgetDao = database.staticWidgetDao()

    @Provides
    fun provideTemplateWidgetDao(database: AppDatabase): TemplateWidgetDao =
        database.templateWidgetDao()

    @Provides
    fun provideNotificationDao(database: AppDatabase): NotificationDao = database.notificationDao()

    @Provides
    fun provideTileDao(database: AppDatabase): TileDao = database.tileDao()

    @Provides
    fun provideFavoritesDao(database: AppDatabase): FavoritesDao = database.favoritesDao()

    @Provides
    fun provideSettingsDao(database: AppDatabase): SettingsDao = database.settingsDao()

    @Provides
    fun provideEntityStateComplicationsDao(database: AppDatabase): EntityStateComplicationsDao = database.entityStateComplicationsDao()
}
