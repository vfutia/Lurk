package com.vfutia.lurk.hilt

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vfutia.lurk.data.db.LurkDatabase
import com.vfutia.lurk.data.db.dao.FavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): LurkDatabase {
        return Room.databaseBuilder(
            context,
            LurkDatabase::class.java, "lurk-database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavoriteDao(db: LurkDatabase) : FavoriteDao = db.favoriteDao()
}