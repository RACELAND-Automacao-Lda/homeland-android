package io.homeland.companion.android.onboarding

import android.util.Log
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.PutDataRequest
import com.google.android.gms.wearable.Wearable
import com.google.android.gms.wearable.WearableListenerService
import dagger.hilt.android.AndroidEntryPoint
import io.homeland.companion.android.common.data.authentication.AuthenticationRepository
import io.homeland.companion.android.common.data.url.UrlRepository
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class WearOnboardingListener : WearableListenerService() {

    @Inject
    lateinit var authenticationUseCase: AuthenticationRepository

    @Inject
    lateinit var urlUseCase: UrlRepository

    override fun onMessageReceived(event: MessageEvent) {
        Log.d("WearOnboardingListener", "onMessageReceived: $event")

        if (event.path == "/request_home_land_instance") {
            val nodeId = event.sourceNodeId
            sendHomeLandInstance(nodeId)
        }
    }

    private fun sendHomeLandInstance(nodeId: String) = runBlocking {
        Log.d("WearOnboardingListener", "sendHomeLandInstance: $nodeId")
        // Retrieve current instance
        val url = urlUseCase.getUrl(false)

        if (url != null) {
            // Put as DataMap in data layer
            val putDataReq: PutDataRequest = PutDataMapRequest.create("/home_land_instance").run {
                dataMap.putString("name", url?.host.toString())
                dataMap.putString("url", url.toString())
                setUrgent()
                asPutDataRequest()
            }
            Wearable.getDataClient(this@WearOnboardingListener).putDataItem(putDataReq).apply {
                addOnSuccessListener { Log.d("WearOnboardingListener", "sendHomeLandInstance: success") }
                addOnFailureListener { Log.d("WearOnboardingListener", "sendHomeLandInstance: failed") }
            }
        }
    }
}
