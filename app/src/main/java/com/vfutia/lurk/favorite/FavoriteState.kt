package com.vfutia.lurk.favorite

import com.vfutia.lurk.model.Favorite

data class FavoriteState (
    var favorites: List<Favorite> = listOf()
)