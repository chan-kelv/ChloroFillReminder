package com.kelvinfocus.chlorofillreminder.ui.addplant

import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kelvinfocus.chlorofillreminder.data.model.CareAction
import com.kelvinfocus.chlorofillreminder.data.model.Plant
import com.kelvinfocus.chlorofillreminder.data.model.TimeFrequencyActionUnit.Companion.toTimeActionUnit
import com.kelvinfocus.chlorofillreminder.data.repository.PlantRepository
import com.kelvinfocus.chlorofillreminder.util.ImageConverter.to64ByteString
import com.kelvinfocus.chlorofillreminder.util.SharedPrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddPlantViewModel @Inject constructor(
    private val sharedPrefManager: SharedPrefManager,
    private val plantRepo: PlantRepository
): ViewModel() {
    private val _newPlantValid = MutableLiveData<Boolean>()
    val newPlantValid: LiveData<Boolean> = _newPlantValid

//    private val _plantNameInvalid = MutableLiveData<Boolean>()
//    val plantNameInvalid: LiveData<Boolean> = _plantNameInvalid

    private val _waterScheduleInvalid = MutableLiveData<Boolean>()
    val waterScheduleInvalid: LiveData<Boolean> = _waterScheduleInvalid
//
//    private val _fertilizerScheduleInvalid = MutableLiveData<Boolean>()
//    val fertilizerScheduleInvalid: LiveData<Boolean> = _fertilizerScheduleInvalid

    fun newPlantValidCheck(
        waterAmount: String?
    ) {
        var valid = false
        if (!waterAmount.isNullOrBlank()) { //is there text
            waterAmount.toIntOrNull()?.let { // is it a number
                valid = it > 0 // is the number positive
            }
        }
        if (!valid) {
            _waterScheduleInvalid.value = true
        }
        _newPlantValid.value = valid
    }

    /**
     * Validity has already been verified
     */
    fun savePlant(
        plantPhoto: ImageView?,
        plantName: String?,
        waterFrequency: String,
        waterFreqUnit: String,
        waterAlarm: String?,
        fertilizerFreq: String?,
        fertilizerFreqUnit: String,
        fertilizerAlarm: String?,
        plantNotes: String?
    ) {
        Timber.d("save plant")
        val validPlantName = if (plantName.isNullOrBlank()) {
            val index = sharedPrefManager.getFromDefaultPref(DEFAULT_PLANT_NAME_INDEX, 0)
            sharedPrefManager.saveToDefaultPref(DEFAULT_PLANT_NAME_INDEX, index + 1)
            "MyPlant $index"
        } else plantName

        val waterAction = waterFreqUnit.toTimeActionUnit()?.let {
            CareAction.toCareActionFromString(waterFrequency, it)
        } ?: run {
            Timber.e("Could not convert water time action")
            return
        }

        // this one is optional so no need to error and return if empty
        val fertilizeAction = fertilizerFreqUnit.toTimeActionUnit()?.let {
            CareAction.toCareActionFromString(fertilizerFreq, it)
        } ?: run { null }

//        val fertilizeAction = CareAction.toCareActionFromString(fertilizerFreq, fertilizerFreqUnit)
        val plant = Plant(
            name = validPlantName,
            plantPhoto = plantPhoto?.to64ByteString(),
            waterAction,
            waterAlarm,
            fertilizeAction,
            fertilizerAlarm,
            plantNotes
        )

        plantRepo.savePlant(plant)
        // TODO save plant to db
        // TODO set up alarms
    }

    companion object {
        const val DEFAULT_PLANT_NAME_INDEX = "default_plant_name_index_key"
    }
}