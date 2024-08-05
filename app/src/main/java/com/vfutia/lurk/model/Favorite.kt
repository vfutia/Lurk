package com.vfutia.lurk.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite (
    @PrimaryKey val subreddit: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Favorite

        return subreddit == other.subreddit
    }

    override fun hashCode(): Int {
        return subreddit.hashCode()
    }
}