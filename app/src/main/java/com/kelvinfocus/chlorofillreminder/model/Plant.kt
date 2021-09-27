package com.kelvinfocus.chlorofillreminder.model

import android.content.Context
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kelvinfocus.chlorofillreminder.model.Plant.PlantDatabase.Companion.PLANT_TABLE_NAME
import com.kelvinfocus.chlorofillreminder.model.TimeFrequencyUnit.Companion.toTimeInterval
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.parcelize.Parcelize

@Parcelize
class Plant(
    var name: String,
    var plantPhoto: String? = null,
    var waterAmount: String,
    var waterUnit: String,
    var waterAlarm: String?,
    var fertilizerAmount: String?,
    var fertilizerUnit: String?,
    var fertilizerAlarm: String?,
    var plantNotes: String?
) : Parcelable {
    var waterCareAction: CareAction? = null
    var fertilizeCareAction: CareAction? = null

    init {
        setWaterAmt(waterAmount, waterUnit)
        setFertilizerAmt(fertilizerAmount, fertilizerUnit)
    }

    private fun setWaterAmt(waterAmount: String?, waterUnit: String?) {
        val waterAmountInt = waterAmount?.toIntOrNull()
        val waterFrequency = waterUnit?.toTimeInterval()
        if (waterAmountInt != null && waterFrequency != null) {
            waterCareAction = CareAction(waterAmountInt, waterFrequency)
        }
    }

    private fun setFertilizerAmt(fertilizerAmount: String?, fertilizerUnit: String?) {
        val fertilizeAmountInt = fertilizerAmount?.toIntOrNull()
        val fertilizeFrequency = fertilizerUnit?.toTimeInterval()
        if (fertilizeAmountInt != null && fertilizeFrequency != null) {
            fertilizeCareAction = CareAction(fertilizeAmountInt, fertilizeFrequency)
        }
    }

    @Entity
    data class PlantEntity(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "plant_name") var name: String,
        @ColumnInfo(name = "water_freq") var waterFrequency: String?,
        @ColumnInfo(name = "water_alarm") var waterAlarm: String?,
        @ColumnInfo(name = "fertilize_freq") var fertilizeFrequency: String?,
        @ColumnInfo(name = "fertilize_alarm") var fertilizeAlarm: String?,
        @ColumnInfo(name = "plant_notes") var notes: String?,
        @ColumnInfo(name = "plant_photo") var plantPhoto: String?,
    ) {

    }

    @Dao
    interface PlantDao {
        @Query("SELECT * FROM $PLANT_TABLE_NAME")
        fun getAll(): List<PlantEntity>

        @Insert
        fun insertPlant(plant: PlantEntity)

        @Delete
        fun deletePlant(plant: PlantEntity)
    }

    @Database(entities = [PlantEntity::class], version = 1)
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