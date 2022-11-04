package com.biped.locations.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biped.locations.settings.SettingsRepository
import com.favoriteplaces.core.coroutines.MutableWarmFlow
import com.favoriteplaces.core.coroutines.launchDefault
import com.favoriteplaces.core.coroutines.launchIO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _instruction = MutableWarmFlow<HomeInstruction>(HomeInstruction.Default)
    val instruction = _instruction.toWarmFlow()

    init {
        loadThemeSettings()
    }

    private fun loadThemeSettings() {
        viewModelScope.launchIO {
            settingsRepository.observeThemeSettings().collect {
                _instruction.post(HomeInstruction.UpdateTheme(it))
            }
        }

    }

    fun routeChanged(route: String) {
        viewModelScope.launchDefault {
            _instruction.emit(HomeInstruction.Navigate(route))
        }
    }
}