package com.kelvinfocus.chlorofillreminder.model

import com.kelvinfocus.chlorofillreminder.model.TimeFrequencyUnit.Companion.toTimeInterval

/**
 * How often that watering/fertilize needs to happen
 * e.g. Watering - every "3 DAYS", every "1 MONTH"
 */
data class CareAction(var frequency: Int, var interval: TimeFrequencyUnit) {
    companion object {
        private const val CARE_DELIMITER = "__"

        fun toParceableString(frequency: Int, interval: TimeFrequencyUnit): String {
            return "$frequency$CARE_DELIMITER${interval.name}"
        }

        fun toCareAction(careString: String): CareAction? {
            val careArray = careString.split(CARE_DELIMITER)
            if (careArray.isNullOrEmpty() || careArray.size < 2) return null
            val frequency = careArray[0].toIntOrNull()
            val interval = careArray[1].toTimeInterval()
            if (frequency == null || interval == null) return null
            return CareAction(frequency, interval)
        }
    }
}
