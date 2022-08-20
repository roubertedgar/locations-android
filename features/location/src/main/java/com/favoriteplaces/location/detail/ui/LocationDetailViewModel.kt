package com.favoriteplaces.location.detail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.favoriteplaces.core.flow.Instruction
import com.favoriteplaces.core.flow.MutableInstructionFlow
import com.favoriteplaces.location.detail.GetLocationDetails
import com.favoriteplaces.location.detail.data.domain.LocationDetail
import com.favoriteplaces.location.detail.data.ui.LocationDetailUIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class LocationDetailViewModel @Inject constructor(
    private val detailsInstructions: LocationDetailInstructions,
    private val getLocationDetails: GetLocationDetails,
    private val scheduleFormatter: ScheduleFormatter
) : ViewModel() {

    private val _viewInstruction = MutableInstructionFlow<Instruction>()
    val viewInstruction = _viewInstruction.toInstructionFlow()

    fun loadLocationDetails(locationId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getLocationDetails(locationId)

            result.onSuccess { onLoadLocationDetailSuccess(it) }
            result.onFailure { _viewInstruction.emit(detailsInstructions.failure()) }
        }
    }

    private suspend fun onLoadLocationDetailSuccess(location: LocationDetail) {
        val locationDetailUiModel = LocationDetailUIModel.fromDomain(location, scheduleFormatter)
        _viewInstruction.emit(Success(locationDetailUiModel))
    }
}
