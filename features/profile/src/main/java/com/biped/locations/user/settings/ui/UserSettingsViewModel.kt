package com.biped.locations.user.settings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biped.locations.user.settings.LoadUserSettingsUseCase
import com.biped.locations.user.settings.SaveUserSettingsUseCase
import com.biped.locations.user.settings.data.toDomainModel
import com.biped.locations.user.settings.data.toUiModel
import com.biped.locations.user.settings.data.UserSettingsUiModel
import com.favoriteplaces.core.coroutines.MutableWarmFlow
import com.favoriteplaces.core.tools.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserSettingsViewModel @Inject constructor(
    private val loadUserSettingsUseCase: LoadUserSettingsUseCase,
    private val saveUserSettingsUseCase: SaveUserSettingsUseCase,
) : ViewModel() {

    private val _instruction = MutableWarmFlow<UserSettingsViewInstruction>(
        UserSettingsViewInstruction.Default
    )
    val instruction = _instruction.toWarmFlow()

    fun loadUserSettings() {
        viewModelScope.launch(DispatcherProvider.IO) {
            loadUserSettingsUseCase().collect { userSettings ->
                _instruction.post(UserSettingsViewInstruction.Success(userSettings.toUiModel()))
            }
        }
    }

    fun changeThemeSettings(settings: UserSettingsUiModel) {
        viewModelScope.launch(DispatcherProvider.IO) {
            _instruction
            saveUserSettingsUseCase(settings.toDomainModel())
        }
    }
}