package io.homeland.companion.android.home.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyListState
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberScalingLazyListState
import io.homeland.companion.android.common.sensors.SensorManager
import io.homeland.companion.android.sensors.SensorReceiver
import io.homeland.companion.android.theme.WearAppTheme
import io.homeland.companion.android.views.ListHeader
import io.homeland.companion.android.views.ThemeLazyColumn
import io.homeland.companion.android.common.R as commonR

@Composable
fun SensorsView(
    onClickSensorManager: (SensorManager) -> Unit
) {
    val scalingLazyListState: ScalingLazyListState = rememberScalingLazyListState()

    WearAppTheme {
        Scaffold(
            positionIndicator = {
                if (scalingLazyListState.isScrollInProgress)
                    PositionIndicator(scalingLazyListState = scalingLazyListState)
            },
            timeText = { TimeText(!scalingLazyListState.isScrollInProgress) }
        ) {
            val sensorManagers = getSensorManagers()
            ThemeLazyColumn(
                state = scalingLazyListState
            ) {
                item {
                    ListHeader(id = commonR.string.sensors)
                }
                items(sensorManagers.size, { sensorManagers[it].name }) { index ->
                    sensorManagers.forEach { manager ->
                        Row {
                            Chip(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                colors = ChipDefaults.secondaryChipColors(),
                                label = {
                                    Text(
                                        text = stringResource(manager.name)
                                    )
                                },
                                onClick = { onClickSensorManager(manager) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun getSensorManagers(): List<SensorManager> {
    return SensorReceiver.MANAGERS.sortedBy { stringResource(it.name) }.filter { it.hasSensor(LocalContext.current) }
}

@Preview
@Composable
private fun PreviewSensorsView() {
    CompositionLocalProvider {
        SensorsView {}
    }
}
