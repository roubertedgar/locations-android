package com.favoriteplaces.location.detail

import com.favoriteplaces.location.data.LocationRepository
import com.favoriteplaces.location.detail.data.domain.LocationDetail
import javax.inject.Inject

internal class GetLocationDetails @Inject constructor(private val locationRepository: LocationRepository) {

    suspend operator fun invoke(locationId: Int): Result<LocationDetail> {
        return try {
            Result.success(locationRepository.findLocationById(locationId))
        } catch (error: Throwable) {
            Result.failure(error)
        }
    }
}