package com.realityexpander.zombieoutbreaklocator.di

import android.app.Application
import androidx.room.Room
import com.realityexpander.zombieoutbreaklocator.data.ZombieMarkerDatabase
import com.realityexpander.zombieoutbreaklocator.data.ZombieMarkerRepositoryImpl
import com.realityexpander.zombieoutbreaklocator.domain.repository.ZombieMarkerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideZombieMarkerDatabase(app: Application): ZombieMarkerDatabase {
        return Room.databaseBuilder(
            app,
            ZombieMarkerDatabase::class.java,
            "parking_marker_db.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideZombieMarkerRepository(db: ZombieMarkerDatabase): ZombieMarkerRepository {
        return ZombieMarkerRepositoryImpl(db.zombieMarkerDao)
    }
}