package io.homeland.companion.android.complications

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.homeland.companion.android.HomeLandApplication
import io.homeland.companion.android.common.data.integration.Entity
import io.homeland.companion.android.common.data.integration.IntegrationRepository
import io.homeland.companion.android.common.data.integration.domain
import io.homeland.companion.android.common.data.websocket.WebSocketRepository
import io.homeland.companion.android.common.data.websocket.WebSocketState
import io.homeland.companion.android.data.SimplifiedEntity
import io.homeland.companion.android.database.wear.EntityStateComplications
import io.homeland.companion.android.database.wear.EntityStateComplicationsDao
import io.homeland.companion.android.database.wear.FavoritesDao
import io.homeland.companion.android.database.wear.getAllFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComplicationConfigViewModel @Inject constructor(
    application: Application,
    private val favoritesDao: FavoritesDao,
    private val integrationUseCase: IntegrationRepository,
    private val webSocketUseCase: WebSocketRepository,
    private val entityStateComplicationsDao: EntityStateComplicationsDao
) : AndroidViewModel(application) {
    companion object {
        const val TAG = "ComplicationConfigViewModel"
    }

    enum class LoadingState {
        LOADING, READY, ERROR
    }

    val app = getApplication<HomeLandApplication>()

    var entities = mutableStateMapOf<String, Entity<*>>()
        private set
    var entitiesByDomain = mutableStateMapOf<String, SnapshotStateList<Entity<*>>>()
        private set
    var entitiesByDomainOrder = mutableStateListOf<String>()
        private set
    val favoriteEntityIds = favoritesDao.getAllFlow().collectAsState()

    var loadingState by mutableStateOf(LoadingState.LOADING)
        private set
    var selectedEntity: SimplifiedEntity? by mutableStateOf(null)
        private set

    init {
        loadEntities()
    }

    private fun loadEntities() {
        viewModelScope.launch {
            if (!integrationUseCase.isRegistered()) {
                loadingState = LoadingState.ERROR
                return@launch
            }
            try {
                // Load initial state
                loadingState = LoadingState.LOADING
                integrationUseCase.getEntities()?.forEach {
                    entities[it.entityId] = it
                }
                updateEntityDomains()

                // Finished initial load, update state
                val webSocketState = webSocketUseCase.getConnectionState()
                if (webSocketState == WebSocketState.CLOSED_AUTH) {
                    loadingState = LoadingState.ERROR
                    return@launch
                }
                loadingState = if (webSocketState == WebSocketState.ACTIVE) {
                    LoadingState.READY
                } else {
                    LoadingState.ERROR
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception while loading entities", e)
                loadingState = LoadingState.ERROR
            }
        }
    }

    private fun updateEntityDomains() {
        val entitiesList = entities.values.toList().sortedBy { it.entityId }
        val domainsList = entitiesList.map { it.domain }.distinct()

        // Create a list with all discovered domains + their entities
        domainsList.forEach { domain ->
            val entitiesInDomain = mutableStateListOf<Entity<*>>()
            entitiesInDomain.addAll(entitiesList.filter { it.domain == domain })
            entitiesByDomain[domain]?.let {
                it.clear()
                it.addAll(entitiesInDomain)
            } ?: run {
                entitiesByDomain[domain] = entitiesInDomain
            }
        }
        entitiesByDomainOrder.clear()
        entitiesByDomainOrder.addAll(domainsList)
    }

    fun setEntity(entity: SimplifiedEntity) {
        selectedEntity = entity
    }

    fun addEntityStateComplication(id: Int, entity: SimplifiedEntity) {
        viewModelScope.launch {
            entityStateComplicationsDao.add(EntityStateComplications(id, entity.entityId))
        }
    }

    /**
     * Convert a Flow into a State object that updates until the view model is cleared.
     */
    private fun <T> Flow<T>.collectAsState(
        initial: T
    ): State<T> {
        val state = mutableStateOf(initial)
        viewModelScope.launch {
            collect { state.value = it }
        }
        return state
    }
    private fun <T> Flow<List<T>>.collectAsState(): State<List<T>> = collectAsState(initial = emptyList())
}
