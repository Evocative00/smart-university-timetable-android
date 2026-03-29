package com.smartuniversity.timetable.feature.softconstraint

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.smartuniversity.timetable.R
import com.smartuniversity.timetable.feature.recommendation.RecommendationResultActivity
import com.smartuniversity.timetable.feature.softconstraint.model.Day
import com.smartuniversity.timetable.feature.softconstraint.model.FreeTimePreference
import com.smartuniversity.timetable.feature.softconstraint.model.SoftConstraint

class SoftConstraintActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_soft_constraint)

        val skipButton: Button = findViewById(R.id.buttonSkip)
        val recommendButton: Button = findViewById(R.id.buttonRecommend)

        skipButton.setOnClickListener {
            navigateToResult(
                SoftConstraint(isSkipped = true)
            )
        }

        recommendButton.setOnClickListener {
            val constraint = collectConstraintFromUi()
            navigateToResult(constraint)
        }
    }

    private fun collectConstraintFromUi(): SoftConstraint {
        val preferredDays = mutableListOf<Day>()

        if (findViewById<CheckBox>(R.id.checkboxMonday).isChecked) preferredDays.add(Day.MONDAY)
        if (findViewById<CheckBox>(R.id.checkboxTuesday).isChecked) preferredDays.add(Day.TUESDAY)
        if (findViewById<CheckBox>(R.id.checkboxWednesday).isChecked) preferredDays.add(Day.WEDNESDAY)
        if (findViewById<CheckBox>(R.id.checkboxThursday).isChecked) preferredDays.add(Day.THURSDAY)
        if (findViewById<CheckBox>(R.id.checkboxFriday).isChecked) preferredDays.add(Day.FRIDAY)

        val freeTimePreference = when (findViewById<RadioGroup>(R.id.radioGroupFreeTime).checkedRadioButtonId) {
            R.id.radioMorning -> FreeTimePreference.MORNING
            R.id.radioAfternoon -> FreeTimePreference.AFTERNOON
            else -> FreeTimePreference.NONE
        }

        val professorInput = findViewById<EditText>(R.id.editPreferredProfessors)
            .text
            .toString()
            .split(",")
            .map { it.trim() }
            .filter { it.isNotEmpty() }

        return SoftConstraint(
            isSkipped = false,
            preferredFreeDays = preferredDays,
            freeTimePreference = freeTimePreference,
            keepLunch12To13Free = findViewById<CheckBox>(R.id.checkboxLunch).isChecked,
            avoidGapOver3Hours = findViewById<CheckBox>(R.id.checkboxAvoidLongGap).isChecked,
            preferredProfessors = professorInput,
            considerTravelTime = findViewById<CheckBox>(R.id.checkboxTravelTime).isChecked,
        )
    }

    private fun navigateToResult(constraint: SoftConstraint) {
        val intent = Intent(this, RecommendationResultActivity::class.java)
        intent.putExtra(RecommendationResultActivity.EXTRA_SOFT_CONSTRAINT, constraint)
        startActivity(intent)
    }
}

