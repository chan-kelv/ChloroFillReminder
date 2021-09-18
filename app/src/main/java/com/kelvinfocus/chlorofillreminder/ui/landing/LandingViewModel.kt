package com.kelvinfocus.chlorofillreminder.ui.landing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kelvinfocus.chlorofillreminder.model.Plant

class LandingViewModel: ViewModel() {
    private val _plantSchedule = MutableLiveData<MutableList<Plant>>()
    val plantSchedule: LiveData<MutableList<Plant>> =_plantSchedule

    /*
        Used to update the upcoming reminder state when a schedule is loaded.
        NOT for when individual items in the list are updated
     */
    fun updatePlantSchedule(schedule: MutableList<Plant>) {
        _plantSchedule.value = schedule
    }
}