package com.kelvinfocus.chlorofillreminder.data.model

import android.os.Parcelable
import com.kelvinfocus.chlorofillreminder.data.model.TimeFrequencyActionUnit.Companion.toTimeActionUnit
import kotlinx.parcelize.Parcelize

/**
 * How often that watering/fertilize needs to happen
 * e.g. Watering - every "3 DAYS", every "1 MONTH"
 */
@Parcelize
data class CareAction(var frequency: Int, var timeFreqUnit: TimeFrequencyActionUnit): Parcelable {
    companion object {
        private const val CARE_DELIMITER = "__"

        fun CareAction.toParceableString(): String {
            return "${this.frequency}$CARE_DELIMITER${this.timeFreqUnit.name}"
        }

        fun toCareActionFromParcable(careString: String): CareAction? {
            val careArray = careString.split(CARE_DELIMITER)
            if (careArray.isNullOrEmpty() || careArray.size < 2) return null
            val frequency = careArray[0].toIntOrNull()
            val interval = careArray[1].toTimeActionUnit()
            if (frequency == null || interval == null) return null
            return CareAction(frequency, interval)
        }

        fun toCareActionFromString(frequencyString: String?, freqUnit: TimeFrequencyActionUnit): CareAction? {
            frequencyString?.toIntOrNull()?.let { frequency ->
                return CareAction(frequency, freqUnit)
            }
            return null
        }
    }
}
