package com.kelvinfocus.chlorofillreminder.data.repository

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kelvinfocus.chlorofillreminder.data.model.Plant
import com.kelvinfocus.chlorofillreminder.data.model.Plant.PlantEntity.Companion.toPlantEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PlantRepository @Inject constructor(
    private val plantDao: PlantDao
) : BaseRepository() {

    fun savePlant(plant: Plant) {
        val plantEntity = plant.toPlantEntity()
        plantDao.insertPlant(plantEntity)
    }
    @Dao
    interface PlantDao {
        @Query("SELECT * FROM PlantEntity")
        fun getAll(): List<Plant.PlantEntity>

        @Insert
        fun insertPlant(plant: Plant.PlantEntity)

        @Delete
        fun deletePlant(plant: Plant.PlantEntity)
    }

    @Database(entities = [Plant.PlantEntity::class], version = 1)
    abstract class PlantDatabase : RoomDatabase() {
        abstract fun PlantDao(): PlantDao

        companion object {
            const val PLANT_TABLE_NAME = "PlantTable"
            const val PLANT_DATABASE_NAME = "PlantDatabase"

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