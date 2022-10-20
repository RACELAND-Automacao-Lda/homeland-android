package io.homeland.companion.android.onboarding.discovery

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint
import io.homeland.companion.android.R
import io.homeland.companion.android.onboarding.OnboardingViewModel
import io.homeland.companion.android.onboarding.authentication.AuthenticationFragment
import io.homeland.companion.android.onboarding.manual.ManualSetupFragment
import javax.inject.Inject
import io.homeland.companion.android.common.R as commonR

@AndroidEntryPoint
class DiscoveryFragment @Inject constructor() : Fragment() {

    companion object {

        private const val TAG = "DiscoveryFragment"
        private const val HOME_LAND = "https://www.home-land.io"
    }

    private val viewModel by activityViewModels<OnboardingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        lifecycle.addObserver(viewModel.homeLandSearcher)

        return ComposeView(requireContext()).apply {
            setContent {
                MdcTheme {
                    DiscoveryView(
                        onboardingViewModel = viewModel,
                        whatIsThisClicked = { openHomeLandHomePage() },
                        manualSetupClicked = { navigateToManualSetup() },
                        instanceClicked = { onInstanceClicked(it) }
                    )
                }
            }
        }
    }

    private fun openHomeLandHomePage() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(HOME_LAND)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        try {
            startActivity(intent)
        } catch (e: Exception) {
            Log.e(TAG, "Unable to load HomeLand home page", e)
            Toast.makeText(context, commonR.string.what_is_this_crash, Toast.LENGTH_LONG).show()
        }
    }

    private fun navigateToManualSetup() {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.content, ManualSetupFragment::class.java, null)
            .addToBackStack(null)
            .commit()
    }

    private fun onInstanceClicked(instance: HomeLandInstance) {
        viewModel.manualUrl.value = instance.url.toString()
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.content, AuthenticationFragment::class.java, null)
            .addToBackStack(null)
            .commit()
    }
}
