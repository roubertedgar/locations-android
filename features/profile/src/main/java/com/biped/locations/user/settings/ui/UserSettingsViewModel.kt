package com.biped.locations.user.settings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biped.locations.user.settings.LoadUserSettingsUseCase
import com.biped.locations.user.settings.SaveUserSettingsUseCase
import com.biped.locations.user.settings.data.UserSettings
import com.favoriteplaces.core.coroutines.MutableWarmFlow
import com.favoriteplaces.core.coroutines.launchIO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class UserSettingsViewModel @Inject constructor(
    private val loadUserSettingsUseCase: LoadUserSettingsUseCase,
    private val saveUserSettingsUseCase: SaveUserSettingsUseCase,
) : ViewModel() {

    private val _instruction =
        MutableWarmFlow<Instruction>(Instruction.Default)
    val instruction = _instruction.toWarmFlow()

    init {
        loadUserSettings()
    }

    private fun loadUserSettings() {
        viewModelScope.launchIO {
            _instruction.post(Instruction.Loading)

            loadUserSettingsUseCase().collect { userSettings ->
                _instruction.post(
                    Instruction.Success(userSettings)
                )
            }
        }
    }

    fun changeThemeSettings(settings: UserSettings) {
        viewModelScope.launchIO {
            saveUserSettingsUseCase(settings)
        }
    }

    fun showUserProfile(userId: String) {
        viewModelScope.launchIO {
            _instruction.emit(Instruction.navigateToProfile(userId))
        }
    }
}