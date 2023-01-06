package biped.works.locations.home

import androidx.lifecycle.ViewModel
import biped.works.coroutines.MutableWarmFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
) : ViewModel() {

    private val _instruction = MutableWarmFlow<HomeInstruction>(HomeInstruction.Default)
    val instruction = _instruction.toWarmFlow()

    fun selectHomeDestination(destination: HomeDestination) {
        _instruction.emit(HomeInstruction.Navigate(destination))
    }
}