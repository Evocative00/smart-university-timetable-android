package com.smartuniversity.timetable.feature.recommendation

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.smartuniversity.timetable.R
import com.smartuniversity.timetable.feature.softconstraint.model.FreeTimePreference
import com.smartuniversity.timetable.feature.softconstraint.model.SoftConstraint

class RecommendationResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommendation_result)

        val summaryText: TextView = findViewById(R.id.textResultSummary)
        val closeButton: Button = findViewById(R.id.buttonClose)

        val softConstraint = getSoftConstraintExtra()

        summaryText.text = if (softConstraint == null) {
            "추천 조건이 없습니다."
        } else {
            softConstraint.toSummaryText()
        }

        closeButton.setOnClickListener { finish() }
    }

    private fun getSoftConstraintExtra(): SoftConstraint? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_SOFT_CONSTRAINT, SoftConstraint::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_SOFT_CONSTRAINT)
        }
    }

    private fun SoftConstraint.toSummaryText(): String {
        if (isSkipped) {
            return "건너뛰기로 기본 추천을 진행했습니다."
        }

        val days = if (preferredFreeDays.isEmpty()) "없음" else preferredFreeDays.joinToString()
        val timePref = when (freeTimePreference) {
            FreeTimePreference.MORNING -> "오전"
            FreeTimePreference.AFTERNOON -> "오후"
            else -> "상관없음"
        }
        val professors = if (preferredProfessors.isEmpty()) "없음" else preferredProfessors.joinToString()

        return """
            공강 요일: $days
            공강 시간대 선호: $timePref
            점심시간(12~13) 비우기: ${if (keepLunch12To13Free) "예" else "아니오"}
            3시간 이상 공강 금지: ${if (avoidGapOver3Hours) "예" else "아니오"}
            선호 교수: $professors
            이동시간 고려: ${if (considerTravelTime) "예" else "아니오"}
        """.trimIndent()
    }

    companion object {
        const val EXTRA_SOFT_CONSTRAINT = "extra_soft_constraint"
    }
}

