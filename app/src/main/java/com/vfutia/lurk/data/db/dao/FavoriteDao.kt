package com.vfutia.lurk.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.vfutia.lurk.model.Favorite

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite ORDER BY subreddit ASC")
    fun getAll(): List<Favorite>

    @Query("SELECT * FROM favorite WHERE subreddit = :subreddit")
    fun isFavorite(subreddit: String): Favorite?

    @Insert
    fun insert(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)
}