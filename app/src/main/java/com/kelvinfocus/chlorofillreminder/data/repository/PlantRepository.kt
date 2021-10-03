package com.kelvinfocus.chlorofillreminder.data.repository

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kelvinfocus.chlorofillreminder.data.model.Plant
import com.kelvinfocus.chlorofillreminder.data.model.Plant.PlantEntity.Companion.toPlantEntity
import com.kelvinfocus.chlorofillreminder.data.repository.PlantRepository.PlantDatabase.Companion.PLANT_TABLE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PlantRepository @Inject constructor(
    private val plantDao: PlantDao
) : BaseRepository() {

    @WorkerThread
    suspend fun savePlant(plant: Plant) {
        val plantEntity = plant.toPlantEntity()
        plantDao.insertPlant(plantEntity)
    }

    @Dao
    interface PlantDao {
        @Query("SELECT * FROM $PLANT_TABLE_NAME")
        suspend fun getAll(): List<Plant.PlantEntity>

        /**
         * Saves a plant entity into the db
         * @return the id of the successful insert
         */
        @Insert (onConflict = OnConflictStrategy.ABORT)
        suspend fun insertPlant(plant: Plant.PlantEntity): Boolean {
            return plant.id > -1
        }

        @Delete
        suspend fun deletePlant(plant: Plant.PlantEntity)
    }

    @Database(entities = [Plant.PlantEntity::class], version = 1)
    abstract class PlantDatabase : RoomDatabase() {
        abstract fun PlantDao(): PlantDao

        companion object {
            const val PLANT_TABLE_NAME = "PlantTable"
            private const val PLANT_DATABASE_NAME = "PlantDatabase"

            fun createDbBuilder(
                @ApplicationContext appContext: Context
            ): Builder<PlantDatabase> {
                return Room.databaseBuilder(
                    appContext,
                    PlantDatabase::class.java,
                    PLANT_DATABASE_NAME
                )
            }
        }
    }
}