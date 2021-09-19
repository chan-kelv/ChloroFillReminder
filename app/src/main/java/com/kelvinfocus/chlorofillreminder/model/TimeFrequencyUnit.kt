package com.kelvinfocus.chlorofillreminder.model

enum class TimeFrequencyUnit(interval: String) {
    DAY("DAY(S)"),
    MONTH("MONTH(S)");

    companion object {
        fun getAsList(): List<String> {
            return listOf(DAY.name, MONTH.name)
        }

        fun String.toTimeInterval(): TimeFrequencyUnit? {
            return when (this) {
                DAY.name -> DAY
                MONTH.name -> MONTH
                else -> null
            }
        }
    }
}