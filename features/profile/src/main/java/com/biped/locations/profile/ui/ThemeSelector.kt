package com.biped.locations.profile.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.biped.locations.profile.data.ui.ThemeSettingsUiModel
import com.biped.locations.profile.settings.ColorSettings
import com.biped.locations.theme.AppTheme
import com.biped.locations.theme.SmallSpacer
import com.biped.locations.theme.components.MediumTitle
import com.biped.locations.theme.components.SegmentButton
import com.biped.locations.theme.components.SegmentItem
import com.biped.locations.theme.components.rememberSegmentState

@Composable
fun ThemeSettingsUi(
    uiModel: ThemeSettingsUiModel,
    onSettingsChanged: (ThemeSettingsUiModel) -> Unit = {}
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MediumTitle(text = "Use dynamic colors")
            Switch(
                checked = uiModel.useDynamicColors,
                onCheckedChange = { onSettingsChanged(uiModel.copy(useDynamicColors = it)) }
            )
        }
        SmallSpacer()
        ColorSchemeSelector(
            colorScheme = uiModel.colorScheme,
            onSchemeSelected = { onSettingsChanged(uiModel.copy(colorScheme = it)) }
        )
    }
}

@Composable
fun ColorSchemeSelector(
    colorScheme: ColorSettings,
    onSchemeSelected: (ColorSettings) -> Unit
) {
    val segments = rememberSegmentState(
        SegmentItem(
            "Dark",
            key = ColorSettings.DARK,
            isSelected = colorScheme == ColorSettings.DARK
        ),
        SegmentItem(
            "Light",
            key = ColorSettings.LIGHT,
            isSelected = colorScheme == ColorSettings.LIGHT
        ),
        SegmentItem(
            "System",
            key = ColorSettings.SYSTEM,
            isSelected = colorScheme == ColorSettings.SYSTEM
        ),
    )

    SegmentButton(
        modifier = Modifier.fillMaxWidth(),
        segments = segments,
        onSelectionChange = { selections ->
            if (selections.isNotEmpty()) onSchemeSelected(selections.first() as ColorSettings)
        }
    )
}

@Preview(name = "Light preview", showBackground = true)
@Composable
private fun ThemeLightConfigPreview() {
    AppTheme {
        Surface {
            ThemeSettingsUi(settingsUi)
        }
    }
}

@Preview(name = "Dark preview", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ThemeDarkConfigPreview() {
    AppTheme {
        Surface {
            ThemeSettingsUi(settingsUi)
        }
    }
}

private val settingsUi = ThemeSettingsUiModel(ColorSettings.DARK, false)