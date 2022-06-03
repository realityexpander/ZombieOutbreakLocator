package com.realityexpander.parkingspotlocator.di

import android.app.Application
import androidx.room.Room
import com.realityexpander.parkingspotlocator.data.ParkingMarkerDatabase
import com.realityexpander.parkingspotlocator.data.ParkingMarkerRepositoryImpl
import com.realityexpander.parkingspotlocator.domain.repository.ParkingMarkerRepository
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
    fun provideParkingMarkerDatabase(app: Application): ParkingMarkerDatabase {
        return Room.databaseBuilder(
            app,
            ParkingMarkerDatabase::class.java,
            "parking_marker_db.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideParkingMarkerRepository(db: ParkingMarkerDatabase): ParkingMarkerRepository {
        return ParkingMarkerRepositoryImpl(db.parkingMarkerDao)
    }
}