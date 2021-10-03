package com.kelvinfocus.chlorofillreminder.hilt

import android.content.Context
import com.kelvinfocus.chlorofillreminder.data.repository.PlantRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Database creation is expensive and should be provided as a singleton
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun providesPlantDatabase(@ApplicationContext context: Context): PlantRepository.PlantDatabase {
        return PlantRepository.PlantDatabase.createDbBuilder(context).build()
//        return Room.databaseBuilder(
//            context,
//            PlantRepository.PlantDatabase::class.java,
//            PlantRepository.PlantDatabase.PLANT_DATABASE_NAME
//        ).build()
    }

    @Provides
    fun providesPlantDao(
        plantDb: PlantRepository.PlantDatabase
    ): PlantRepository.PlantDao {
        return plantDb.PlantDao()
    }
}