package com.kelvinfocus.chlorofillreminder.ui.addplant

import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelvinfocus.chlorofillreminder.data.model.CareAction
import com.kelvinfocus.chlorofillreminder.data.model.Plant
import com.kelvinfocus.chlorofillreminder.data.model.TimeFrequencyActionUnit.Companion.toTimeActionUnit
import com.kelvinfocus.chlorofillreminder.data.repository.PlantRepository
import com.kelvinfocus.chlorofillreminder.util.ImageConverter.to64ByteString
import com.kelvinfocus.chlorofillreminder.util.SharedPrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddPlantViewModel @Inject constructor(
    private val sharedPrefManager: SharedPrefManager,
    private val plantRepo: PlantRepository
): ViewModel() {
    /**
     * Tracks if the plant was saved successfully to the db
     */
    private val _saveSuccess = MutableLiveData<Boolean>()
    val saveSuccess: LiveData<Boolean> = _saveSuccess

    /**
     * Tracks if the ENTIRE form was filled in correctly
     */
    private val _newPlantValid = MutableLiveData<Boolean>()
    val newPlantValid: LiveData<Boolean> = _newPlantValid

    /**
     * Tracks if the water schedule was incomplete
     */
    private val _waterScheduleInvalid = MutableLiveData<Boolean>()
    val waterScheduleInvalid: LiveData<Boolean> = _waterScheduleInvalid

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

        val plant = Plant(
            name = validPlantName,
            plantPhoto = plantPhoto?.to64ByteString(),
            waterAction,
            waterAlarm,
            fertilizeAction,
            fertilizerAlarm,
            plantNotes
        )

        viewModelScope.launch {
            plantRepo.savePlant(plant)
        }
        // TODO set up alarms
    }

    companion object {
        const val DEFAULT_PLANT_NAME_INDEX = "default_plant_name_index_key"
    }
}