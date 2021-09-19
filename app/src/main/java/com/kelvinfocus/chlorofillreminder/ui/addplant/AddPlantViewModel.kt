package com.kelvinfocus.chlorofillreminder.ui.addplant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddPlantViewModel: ViewModel() {
    private val _newPlantValid = MutableLiveData<Boolean>()
    val newPlantValid: LiveData<Boolean> = _newPlantValid

    private val _waterScheduleInvalid = MutableLiveData<Boolean>()
    val waterScheduleInvalid: LiveData<Boolean> = _waterScheduleInvalid

    private val _fertilizerScheduleInvalid = MutableLiveData<Boolean>()
    val fertilizerScheduleInvalid: LiveData<Boolean> = _waterScheduleInvalid
}