package com.syu.smarttimetable.data.model.user;

// 사용자 프로필 정보 저장 클래스

import data.model.common.ClassParity; //홀,짝 반 구분 목적 임포트

/*userprofile을 임포트 하게 된다면 private로 접근지정자 지정 해놓은 애들은 캡슐화를 위해
getter / setter로 접근할 수 있게 캡슐화
* */
public class UserProfile {

    // Room 확장 시: userId는 @PrimaryKey 후보입니다.
    private String userId; //고유 식별자, (학번이 될 수도 있음)
    private String name; //이름
    private String department; //학과
    private String major; //세부 전공 ( Ex) 컴퓨터공학과 내에서 소프트웨어,컴공 전공 등 )
    private int grade; //학년
    private ClassParity classParity;//홀, 짝, 전체가능 반 표시

    //디폴트 생성자 생성
    public UserProfile() {
        this.classParity = ClassParity.ALL; //null 대신 기본값 ALL로 설정( 제한 없음 )
        // 아무 값도 안 넣으면 null로 인해 반 비교 전에 null pointer exception 방지
    }

    //생성자
    // 객체 생성시 필요한 값 한 번에 할당 가능
    /*
    Ex )
    UserProfile user = new UserProfile(
    "20230001", 학번
    "홍길동", 이름
    "컴퓨터공학과", 학과
    "소프트웨어전공", 세부 전공
    2, 힉냔
    ClassParity.ODD); 반(홀, 짝, ALL)
     */
    public UserProfile(String userId, String name, String department, String major, int grade, ClassParity classParity) {
        this.userId = userId;
        this.name = name;
        this.department = department;
        this.major = major;
        this.grade = grade;
        // null값 방지를 위한 삼항 연산자 추가
        // 전달받은 classParity가 null이면 -> ClassParity.ALL
        // null이 아니면 전달받은 원래 값 사용 ( 홀 짝 )
        this.classParity = classParity == null ? ClassParity.ALL : classParity;
    }

    // 필드 값을 꺼내는 매서드 getter 사용
    // 필드 값을 설정하는 메서드 setter 사용
    // private + getter/setter는 캡슐화

    // 사용자 ID 반환
    public String getUserId() {
        return userId;
    }

    // 사용자 아이디 값 수정( DB에서 읽은 값 반영 )
    public void setUserId(String userId) {
        this.userId = userId;
    }

    // 이름 반환
    public String getName() {
        return name;
    }

    // 이름 설정
    public void setName(String name) {
        this.name = name;
    }

    // 학과 반환
    public String getDepartment() {
        return department;
    }

    // 학과 설정
    public void setDepartment(String department) {
        this.department = department;
    }

    // 전공 반환
    public String getMajor() {
        return major;
    }

    // 전공 설정
    public void setMajor(String major) {
        this.major = major;
    }

    // 학년 반환
    public int getGrade() {
        return grade;
    }

    // 학년 설정
    public void setGrade(int grade) {
        this.grade = grade;
    }

    // 홀짝 반 반환
    public ClassParity getClassParity() {
        return classParity;
    }

    // 홀짝 반 설정( 마찬가지로 홀짝 구분 삼항 연산자로 nullpointexception 방지)
    // 실수로 null 값이 들어가더라도 setClassParity 메서드에서 null 체크 후 기본값 ALL로 설정하여 안정성 확보
    public void setClassParity(ClassParity classParity) {
        this.classParity = classParity == null ? ClassParity.ALL : classParity;
    }

    // toSpring() -> 객체를 문자열로 표현할 때 호출되는 메서드
    // Ex ) System.out.prinln(userProfile); -> toString 값 출력
    // 객체 내용을 문자열로 보기 쉽게 출력하기 위한 메서드
    @Override
    public String toString() {
        return "UserProfile{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", major='" + major + '\'' +
                ", grade=" + grade +
                ", classParity=" + classParity +
                '}';
    }
}

