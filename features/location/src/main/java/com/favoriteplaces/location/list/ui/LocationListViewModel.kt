package com.favoriteplaces.location.list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.favoriteplaces.core.coroutines.MutableWarmFlow
import com.favoriteplaces.core.coroutines.launchDefault
import com.favoriteplaces.core.coroutines.launchIO
import com.favoriteplaces.location.list.LoadLocations
import com.favoriteplaces.location.list.data.Location
import com.favoriteplaces.location.list.data.ui.LocationUIModel
import javax.inject.Inject

internal class LocationListViewModel
@Inject constructor(
    private val locationListInstructions: LocationListInstructions,
    private val loadLocations: LoadLocations
) : ViewModel() {

    private val _instruction = MutableWarmFlow<Instruction>()
    val instruction = _instruction.toWarmFlow()

    fun fetchLocations() {

        viewModelScope.launchIO {
            _instruction.post(locationListInstructions.loading())

            val locationResult = loadLocations()
            locationResult.onSuccess { locations ->
                onLocationLoadSuccess(locations)
            }

            locationResult.onFailure {
                _instruction.emit(locationListInstructions.failure())
            }
        }
    }

    private suspend fun onLocationLoadSuccess(locations: List<Location>) {
        val locationsUI = locations.map { LocationUIModel.fromDomain(it) }

        _instruction.post(locationListInstructions.success(locationsUI))
    }

    fun onLocationSelected(locationUIModel: LocationUIModel) {
        viewModelScope.launchDefault {
            _instruction.emit(locationListInstructions.navigateToLocationDetails(locationUIModel))
        }
    }
}