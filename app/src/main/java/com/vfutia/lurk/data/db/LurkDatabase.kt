package com.vfutia.lurk.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vfutia.lurk.data.db.dao.FavoriteDao
import com.vfutia.lurk.model.Favorite
import com.vfutia.lurk.model.Subreddit

@Database(entities = [
    Favorite::class
], version = 1)
internal abstract class LurkDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}