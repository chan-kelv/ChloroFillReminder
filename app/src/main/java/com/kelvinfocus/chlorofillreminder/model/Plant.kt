package com.kelvinfocus.chlorofillreminder.model

import android.content.Context
import android.os.Parcelable
import android.widget.ImageView
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
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.parcelize.Parcelize

@Parcelize
class Plant(var name: String,
            var plantPhoto: String? = null,
            var waterAmount: String,
            var waterUnit: String,
            var waterAlarm: String?,
            var fertilizerAmount: String?,
            var fertilizerUnit: String?,
            var fertilizerAlarm: String?,
            var plantNotes: String?): Parcelable {
//    constructor(name: String,
//                plantPhoto: ImageView?,
//                waterAmount: String,
//                waterUnit: String,
//                waterAlarm: String?,
//                fertilizerAmount: String?,
//                fertilizerUnit: String?,
//                fertilizerAlarm: String?,
//                plantNotes: String?): this(name) {
//            if (waterAmount != null && waterUnit != null) {
//                setWaterAmt(waterAmount, waterUnit)
//            }
//            if (fertilizerAmount != null && fertilizerUnit != null) [
//                setFertilizerAmt(fertilizerAmount, fertilizerUnit)
//            ]
//    }

    private fun setFertilizerAmt(fertilizerAmount: Unit, fertilizerUnit: Unit) {
        TODO("Not yet implemented")
    }

    private fun setWaterAmt(waterAmount: String, waterUnit: String) {
        TODO("Not yet implemented")
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
    abstract class PlantDatabase: RoomDatabase() {
        abstract fun PlantDao(): PlantDao

        companion object {
            const val PLANT_TABLE_NAME = "PlantTable"
            const val PLANT_DATABASE_NAME = "PlantDatabase"

            fun createDbBuilder(
                @ApplicationContext appContext: Context): Builder<PlantDatabase> {
                return Room.databaseBuilder(
                    appContext,
                    PlantDatabase::class.java,
                    PLANT_DATABASE_NAME
                )
            }
        }
    }
}