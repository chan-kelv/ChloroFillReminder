package com.kelvinfocus.chlorofillreminder.ui.addplant

import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class AddPlantViewModel: ViewModel() {
    private val _newPlantValid = MutableLiveData<Boolean>()
    val newPlantValid: LiveData<Boolean> = _newPlantValid
//
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
        waterAmount: String,
        waterUnit: String,
        waterAlarm: String?,
        fertilizerAmount: String?,
        fertilizerUnit: String?,
        fertilizerAlarm: String?,
        plantNotes: String?
    ) {
        Timber.d("save plant")
    }
}