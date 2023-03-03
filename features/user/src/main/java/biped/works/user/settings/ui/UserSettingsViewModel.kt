package biped.works.user.settings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biped.works.coroutines.MutableViewStateFlow
import biped.works.coroutines.launchIO
import biped.works.user.settings.ObserveUseSettingsUseCase
import biped.works.user.settings.SaveUserSettingsUseCase
import biped.works.user.settings.data.UserSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
internal class UserSettingsViewModel @Inject constructor(
    private val observeUseSettingsUseCase: ObserveUseSettingsUseCase,
    private val saveUserSettingsUseCase: SaveUserSettingsUseCase,
) : ViewModel() {

    private val _instruction = MutableViewStateFlow<Instruction>(Instruction.UpdateSettings())
    val instruction = _instruction.toViewStateFlow()

    init {
        loadUserSettings()
    }

    private fun loadUserSettings() {
        _instruction.update { copy(isLoading = true) }

        observeUseSettingsUseCase()
            .onEach { userSettings -> _instruction.update { copy(settings = userSettings, isLoading = false) } }
            .launchIn(viewModelScope)
    }

    fun changeThemeSettings(settings: UserSettings) {
        viewModelScope.launchIO {
            saveUserSettingsUseCase(settings)
        }
    }

    fun showUserProfile(userId: String) {
        _instruction.emit(Instruction.navigateToProfile(userId))
    }
}