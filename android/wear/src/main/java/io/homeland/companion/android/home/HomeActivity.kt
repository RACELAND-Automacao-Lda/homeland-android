package io.homeland.companion.android.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.homeland.companion.android.home.views.LoadHomePage
import io.homeland.companion.android.onboarding.OnboardingActivity
import io.homeland.companion.android.onboarding.integration.MobileAppIntegrationActivity
import io.homeland.companion.android.sensors.SensorReceiver
import io.homeland.companion.android.sensors.SensorWorker
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : ComponentActivity(), HomeView {

    @Inject
    lateinit var presenter: HomePresenter

    private val mainViewModel by viewModels<MainViewModel>()

    companion object {
        private const val TAG = "HomeActivity"

        fun newInstance(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get rid of me!
        presenter.init(this)

        presenter.onViewReady()
        setContent {
            LoadHomePage(mainViewModel)
        }

        mainViewModel.init(presenter)
    }

    override fun onResume() {
        super.onResume()
        SensorWorker.start(this)

        initAllSensors()
    }

    private fun initAllSensors() {
        for (manager in SensorReceiver.MANAGERS) {
            for (basicSensor in manager.getAvailableSensors(this)) {
                manager.isEnabled(this, basicSensor.id)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        SensorWorker.start(this)
    }

    override fun onDestroy() {
        presenter.onFinish()
        super.onDestroy()
    }

    override fun displayOnBoarding() {
        val intent = OnboardingActivity.newInstance(this)
        startActivity(intent)
        finish()
    }

    override fun displayMobileAppIntegration() {
        val intent = MobileAppIntegrationActivity.newInstance(this)
        startActivity(intent)
        finish()
    }
}
