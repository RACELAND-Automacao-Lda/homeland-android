package io.homeland.companion.android.onboarding.manual

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint
import io.homeland.companion.android.R
import io.homeland.companion.android.onboarding.OnboardingViewModel
import io.homeland.companion.android.onboarding.authentication.AuthenticationFragment

@AndroidEntryPoint
class ManualSetupFragment : Fragment() {

    private val viewModel by activityViewModels<OnboardingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MdcTheme {
                    ManualSetupView(
                        onboardingViewModel = viewModel,
                        connectedClicked = { connectClicked() }
                    )
                }
            }
        }
    }

    private fun connectClicked() {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.content, AuthenticationFragment::class.java, null)
            .addToBackStack(null)
            .commit()
    }
}
