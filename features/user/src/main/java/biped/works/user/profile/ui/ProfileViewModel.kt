package biped.works.user.profile.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biped.works.coroutines.MutableWarmFlow
import biped.works.coroutines.launchIO
import biped.works.user.ProfileDestination.Companion.USER_ID_ARG
import biped.works.user.profile.ObserveUserUseCase
import biped.works.user.profile.SaveUserUseCase
import biped.works.user.profile.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val observeUser: ObserveUserUseCase,
    private val saveUser: SaveUserUseCase
) : ViewModel() {

    private val _instruction = MutableWarmFlow<Instruction>(Instruction.Default)
    val instruction = _instruction.toWarmFlow()

    init {
        val userId = stateHandle.get<String>(USER_ID_ARG).orEmpty()
        loadUserProfile(userId)
    }

    private fun loadUserProfile(userId: String) {
        viewModelScope.launch {
            observeUser().collect { user ->
                _instruction.post(Instruction.UpdateUser(user))
            }
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launchIO {
            saveUser(user)
        }
    }
}