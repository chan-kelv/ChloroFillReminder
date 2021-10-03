package com.kelvinfocus.chlorofillreminder.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kelvinfocus.chlorofillreminder.data.model.CareAction.Companion.toParceableString
import com.kelvinfocus.chlorofillreminder.data.repository.PlantRepository
import kotlinx.parcelize.Parcelize

@Parcelize
class Plant(
    var name: String,
    var plantPhoto: String? = null,
    var waterAction: CareAction,
    var waterAlarm: String?,
    var fertilizerAction: CareAction?,
    var fertilizerAlarm: String?,
    var plantNotes: String?
) : Parcelable {
    @Entity(tableName = PlantRepository.PlantDatabase.PLANT_TABLE_NAME)
    data class PlantEntity(
        @ColumnInfo(name = "plant_name") var name: String,
        @ColumnInfo(name = "water_freq") var waterAction: String?,
        @ColumnInfo(name = "water_alarm") var waterAlarm: String?,
        @ColumnInfo(name = "fertilize_freq") var fertilizeAction: String?,
        @ColumnInfo(name = "fertilize_alarm") var fertilizeAlarm: String?,
        @ColumnInfo(name = "plant_notes") var notes: String?,
        /*
            // TODO compress to blob
            https://stackoverflow.com/questions/37873380/how-to-reduce-image-size-into-1mb
            https://stackoverflow.com/a/46356934
         */
        @ColumnInfo(name = "plant_photo") var plantPhoto: String?,
    ) {
        @PrimaryKey(autoGenerate = true) var id: Int = 0

        companion object {
            fun Plant.toPlantEntity(): PlantEntity {
                return PlantEntity(
                    this.name,
                    this.waterAction.toParceableString(),
                    this.waterAlarm,
                    this.fertilizerAction?.toParceableString(),
                    this.fertilizerAlarm,
                    this.plantNotes,
                    plantPhoto
                )
            }
        }
    }

//    @Dao
//    interface PlantDao {
//        @Query("SELECT * FROM $PLANT_TABLE_NAME")
//        fun getAll(): List<PlantEntity>
//
//        @Insert
//        fun insertPlant(plant: PlantEntity)
//
//        @Delete
//        fun deletePlant(plant: PlantEntity)
//    }
//
//    @Database(entities = [PlantEntity::class], version = 1)
//    abstract class PlantDatabase : RoomDatabase() {
//        abstract fun PlantDao(): PlantDao
//
//        companion object {
//            const val PLANT_TABLE_NAME = "PlantTable"
//            const val PLANT_DATABASE_NAME = "PlantDatabase"
//
//            fun createDbBuilder(
//                @ApplicationContext appContext: Context
//            ): Builder<PlantDatabase> {
//                return Room.databaseBuilder(
//                    appContext,
//                    PlantDatabase::class.java,
//                    PLANT_DATABASE_NAME
//                )
//            }
//        }
//    }
}