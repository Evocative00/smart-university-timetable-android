# 안드로이드 추천 시간표 즐겨찾기 기능 구현 완료 🎉

## 📋 구현 내용

### 1️⃣ UI 반영 (item_recommendation.xml)
✅ **완료**
- 추천 결과 시간표 카드의 강의명 오른쪽에 즐겨찾기 버튼(별 아이콘) 추가
- 제목과 버튼을 가로로 배치하는 LinearLayout 구조 적용
- 즐겨찾기 상태에 따라 빈 별(ic_star_border) / 노란 별(ic_star_filled)로 변환

**변경 사항:**
- `tv_course_name`을 0dp + layout_weight="1"로 변경하여 제목이 너비를 차지하도록 함
- ImageButton `btn_favorite` 추가 (36dp × 36dp, 아이콘 재질 디자인 ripple 효과)

---

### 2️⃣ RecyclerView 어댑터 구현 (RecommendationListAdapter.java)
✅ **완료 (신규 생성)**

**주요 기능:**
- `favoriteIndex (int)` 필드로 단일 선택 관리
  - `-1`: 즐겨찾기 없음
  - `0~n`: 해당 인덱스의 항목이 즐겨찾기 상태
  
- `OnFavoriteClickListener` 인터페이스로 Activity에 이벤트 전달
  
- `setFavorite(int position)` 메서드
  - 새로운 카드 클릭 시 이전 선택 자동 해제
  - `notifyItemChanged()`로 선택적 UI 업데이트 (효율성 증대)

**어댑터 메서드:**
```java
void setFavoriteIndex(int index)                           // 초기 즐겨찾기 인덱스 설정
int getFavoriteIndex()                                     // 현재 즐겨찾기 인덱스 조회
void setFavorite(int position)                             // 새로운 즐겨찾기 설정
void clearFavorite()                                       // 즐겨찾기 해제
void setOnFavoriteClickListener(Listener listener)         // 콜백 리스너 등록
```

---

### 3️⃣ SharedPreferences 저장 (RecommendationPreferenceManager.java)
✅ **완료 (신규 생성)**

**기능:**
- `PREF_NAME = "recommendation_prefs"`로 별도 Preference 파일 생성
- `KEY_FAVORITE_INDEX` 키로 즐겨찾기 인덱스 저장/로드

**메서드:**
```java
void saveFavoriteIndex(int favoriteIndex)   // 즐겨찾기 인덱스 영구 저장
int getFavoriteIndex()                      // 저장된 인덱스 로드 (기본값: -1)
void clearFavorite()                        // 저장된 즐겨찾기 초기화
```

---

### 4️⃣ Activity 통합 (RecommendationActivity.java)
✅ **완료 (수정)**

**추가된 필드:**
```java
private RecyclerView lectureListContainer;              // LinearLayout에서 RecyclerView로 변경
private RecommendationListAdapter recommendationListAdapter;
private RecommendationPreferenceManager preferenceManager;
```

**주요 메서드:**
- `onCreate()`: `preferenceManager` 초기화
  
- `renderLectureListWithAdapter()` 메서드 (신규)
  - 강의 목록을 정렬
  - `RecommendationListAdapter` 생성
  - 저장된 `favoriteIndex` 로드하여 어댑터에 설정
  - 즐겨찾기 콜백 설정:
    - 즐겨찾기 변경 시 SharedPreferences에 저장
    - 즐겨찾기 해제 시 초기화

**레이아웃 변경:**
- `activity_recommendation.xml`의 `lecture_list_container`를 `LinearLayout`에서 `RecyclerView`로 변경

---

### 5️⃣ 별 아이콘 리소스
✅ **완료**

**생성된 파일:**
1. `ic_star_border.xml` - 빈 별 (회색)
2. `ic_star_filled.xml` - 노란 별 (#FFD700)

**색상 추가:**
- `values/colors.xml`: `smart_warning_yellow` (#FFD700) 추가
- `values-night/colors.xml`: `smart_warning_yellow` (#FFD700) 추가 (다크 모드 지원)

---

### 6️⃣ 의존성 추가
✅ **완료**

**build.gradle.kts:**
```gradle
implementation("androidx.recyclerview:recyclerview:1.3.2")
```

---

## 🎯 동작 흐름

```
1. 앱 시작
   ↓
2. RecommendationActivity.onCreate()
   - RecommendationPreferenceManager 초기화
   ↓
3. renderCurrentRecommendation()
   ↓
4. renderLectureListWithAdapter()
   - 강의 목록 정렬
   - RecommendationListAdapter 생성
   - 저장된 favoriteIndex 로드
   ↓
5. RecyclerView 표시 (저장된 즐겨찾기 상태 표시)
   ↓
6. 사용자가 별 버튼 클릭
   - RecommendationListAdapter.setFavorite() 호출
   - 이전 선택 자동 해제
   - UI 업데이트 (notifyItemChanged)
   ↓
7. OnFavoriteClickListener 콜백
   - preferenceManager.saveFavoriteIndex() 호출
   - SharedPreferences에 저장
   ↓
8. 앱 종료 후 재시작
   - 저장된 favoriteIndex 로드
   - 해당 카드가 노란 별로 표시됨 ✨
```

---

## 📱 사용자 경험

| 상황 | 동작 |
|------|------|
| 앱 처음 시작 | 모든 카드가 빈 별(회색) 표시 |
| 카드 별 클릭 | 해당 카드만 노란 별로 변경, 이전 선택 해제 |
| 선택된 카드 별 다시 클릭 | 즐겨찾기 해제 (빈 별로 변경) |
| 다른 시간표 카드로 이동 후 돌아옴 | 이전 선택한 즐겨찾기 유지 |
| 앱 재시작 | 마지막 선택한 즐겨찾기 복원 |

---

## ✨ 주요 특징

✅ **단일 선택**: `boolean` 대신 `int favoriteIndex`로 관리하여 정확한 상태 제어
✅ **효율적 UI 업데이트**: `notifyDataSetChanged()` 대신 `notifyItemChanged()` 사용
✅ **데이터 영구 저장**: SharedPreferences로 앱 재시작 후에도 상태 유지
✅ **명확한 콜백**: `OnFavoriteClickListener` 인터페이스로 이벤트 처리
✅ **다크 모드 지원**: 색상 리소스를 `values-night`에도 추가
✅ **재질 디자인**: ImageButton에 ripple 효과 적용

---

## 🛠️ 구현된 파일 목록

| 파일 | 상태 | 설명 |
|------|------|------|
| `item_recommendation.xml` | 수정 ✏️ | 즐겨찾기 버튼 UI 추가 |
| `RecommendationListAdapter.java` | 신규 ✨ | RecyclerView 어댑터 |
| `RecommendationActivity.java` | 수정 ✏️ | RecyclerView 통합, 선호도 관리 |
| `RecommendationPreferenceManager.java` | 신규 ✨ | SharedPreferences 관리 |
| `activity_recommendation.xml` | 수정 ✏️ | LinearLayout → RecyclerView |
| `ic_star_border.xml` | 신규 ✨ | 빈 별 아이콘 |
| `ic_star_filled.xml` | 신규 ✨ | 노란 별 아이콘 |
| `colors.xml` | 수정 ✏️ | `smart_warning_yellow` 추가 |
| `colors.xml (night)` | 수정 ✏️ | `smart_warning_yellow` 추가 |
| `build.gradle.kts` | 수정 ✏️ | RecyclerView 의존성 추가 |

---

## 🎓 사용 예시

### 1. 앱 시작 시 저장된 즐겨찾기 로드
```java
// RecommendationActivity.renderLectureListWithAdapter()
int savedFavoriteIndex = preferenceManager.getFavoriteIndex();
recommendationListAdapter.setFavoriteIndex(savedFavoriteIndex);
// RecyclerView 표시 시 savedFavoriteIndex 위치의 카드에 노란 별 표시
```

### 2. 즐겨찾기 버튼 클릭
```java
// RecommendationListAdapter.onBindViewHolder()
holder.btnFavorite.setOnClickListener(v -> {
    if (favoriteIndex == position) {
        clearFavorite();  // 이미 선택된 상태면 해제
    } else {
        setFavorite(position);  // 새로운 선택
    }
});
```

### 3. 콜백으로 저장
```java
recommendationListAdapter.setOnFavoriteClickListener((position, isFavorite) -> {
    if (isFavorite) {
        preferenceManager.saveFavoriteIndex(position);  // 저장
    } else {
        preferenceManager.clearFavorite();  // 초기화
    }
});
```

---

## ✅ 테스트 체크리스트

- [ ] 앱 시작 시 모든 별이 빈 상태(회색)인지 확인
- [ ] 임의의 카드 별 클릭 → 해당 별만 노란색으로 변경
- [ ] 다른 카드 별 클릭 → 이전 별이 회색으로 돌아오고 새 별이 노란색으로 변경
- [ ] 선택된 카드 별 다시 클릭 → 빈 별로 돌아옴
- [ ] 앱 종료 후 재시작 → 마지막 선택한 즐겨찾기가 복원되는지 확인
- [ ] 다른 추천 시간표로 네비게이션 후 돌아옴 → 이전 선택한 즐겨찾기 유지 확인
- [ ] 다크 모드에서 별 색상이 올바르게 표시되는지 확인

---

## 📝 참고 사항

- `favoriteIndex`는 **추천 시간표 화면 내**에서의 카드 위치 인덱스입니다
- 다른 추천 결과 화면으로 이동하면 별도의 `favoriteIndex`를 저장/로드합니다
- SharedPreferences는 현재 추천 결과의 `favoriteIndex`만 저장합니다 (추천 결과별 추적 필요 시 추가 구현)

---

**구현 완료일**: 2026-05-30 ✅

