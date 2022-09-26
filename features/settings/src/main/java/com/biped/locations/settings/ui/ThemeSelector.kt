package com.biped.locations.settings.ui

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
import com.biped.locations.theme.AppTheme
import com.biped.locations.theme.ColorTheme
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
    colorScheme: ColorTheme,
    onSchemeSelected: (ColorTheme) -> Unit
) {
    val segmentState = rememberSegmentState(
        SegmentItem(
            "Dark",
            key = ColorTheme.DARK,
            isSelected = colorScheme == ColorTheme.DARK
        ),
        SegmentItem(
            "Light",
            key = ColorTheme.LIGHT,
            isSelected = colorScheme == ColorTheme.LIGHT
        ),
        SegmentItem(
            "System",
            key = ColorTheme.SYSTEM,
            isSelected = colorScheme == ColorTheme.SYSTEM
        ),
    )

    SegmentButton(
        modifier = Modifier.fillMaxWidth(),
        segmentState = segmentState,
        onSelectionChange = { selections ->
            if (selections.isNotEmpty()) onSchemeSelected(selections.first() as ColorTheme)
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

private val settingsUi = ThemeSettingsUiModel(ColorTheme.DARK, false)