# CLAUDE.md

## 핵심 원칙

**1. 코딩 전 사고**
- 가정을 명시적으로 표현하고 불명확한 부분을 질문하세요.
- 여러 해석이 있으면 그것들을 제시하되, 조용히 선택하지 마세요.

**2. 단순성 우선**
최소한의 코드로 문제를 해결하되, 요청받지 않은 기능을 추가하지 마세요.

**3. 정확한 변경**
기존 코드를 수정할 때는 요청된 부분만 건드리세요. 불필요한 정리나 개선은 피하세요.

**4. 목표 중심 실행**
성공 기준을 명확히 정의하고 검증 가능한 단계로 작업하세요.

## 프로젝트 개요

- **앱명:** SmartTimetable
- **패키지:** com.syu.smarttimetable
- **언어:** Java (Android)
- **minSdk:** 24 / **targetSdk:** 34
- **Firebase:** Auth + Firestore 사용

## 네비게이션 흐름

```
LoginActivity
├── 기본정보 있음 → MainNavigationActivity
└── 기본정보 없음 → UserInfoActivity → MainNavigationActivity

MainNavigationActivity (바텀 네비게이션)
├── TimetableFragment   - 추천 시간표 / 즐겨찾기
├── CalendarFragment    - 학사 캘린더
├── SchoolHomeFragment  - 학교 홈페이지 (WebView)
├── ConstraintFragment  - 하드/소프트 제약 설정
└── ProfileFragment     - 사용자 정보 수정

HardConstraintActivity → SoftConstraintActivity → MainNavigationActivity
```
