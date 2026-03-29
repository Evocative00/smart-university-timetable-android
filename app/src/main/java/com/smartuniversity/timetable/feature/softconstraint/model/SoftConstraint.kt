package com.smartuniversity.timetable.feature.softconstraint.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SoftConstraint(
    val isSkipped: Boolean = false,
    val preferredFreeDays: List<Day> = emptyList(),
    val freeTimePreference: FreeTimePreference = FreeTimePreference.NONE,
    val keepLunch12To13Free: Boolean = false,
    val avoidGapOver3Hours: Boolean = false,
    val preferredProfessors: List<String> = emptyList(),
    val considerTravelTime: Boolean = false,
) : Parcelable

enum class Day {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
}

enum class FreeTimePreference {
    NONE,
    MORNING,
    AFTERNOON,
}

