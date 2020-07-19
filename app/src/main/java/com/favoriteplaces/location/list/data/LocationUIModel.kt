package com.favoriteplaces.location.list.data

import com.favoriteplaces.location.Location

data class LocationUIModel(
    val id: Int,
    val name: String,
    val review: Number,
    val type: String,
    val image: String
) {

    companion object {
        fun fromDomain(location: Location) =
            LocationUIModel(
                location.id,
                location.name,
                location.review,
                location.type,
                "https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg?cs=srgb&dl=adult-beard-boy-casual-220453.jpg&fm=jpg"
            )
    }
}