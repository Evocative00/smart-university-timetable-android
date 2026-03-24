package com.syu.smarttimetable.ui.onboarding;

public final class DepartmentMajorMapper {

    private DepartmentMajorMapper() {
    }

    public static String[] getMajorDetails(String department) {
        if (department == null) {
            return new String[0];
        }

        switch (department) {
            case "컴퓨터공학부":
                return new String[]{"컴퓨터공학 전공", "소프트웨어 전공"};

            case "인공지능융합학부":
                return new String[]{"인공지능공학 전공", "지능형반도체 전공", "경영정보시스템 전공"};

            case "아트앤디자인학과":
                return new String[]{"미술 전공", "디자인 전공"};

            case "음악학과":
                return new String[]{"성악", "피아노", "관현악", "작곡"};

            case "항공관광외국어학부":
                return new String[]{"관광경영 전공", "동양어문화 전공"};

            case "체육학과":
                return new String[]{"축구 전공", "배구 전공", "테니스 전공", "배드민턴 전공", "수영 전공", "실용무용 전공"};

            case "화학생명과학과":
                return new String[]{"화학 전공", "생명과학 전공"};

            default:
                return new String[0];
        }
    }

    public static boolean hasMajorDetails(String department) {
        return getMajorDetails(department).length > 0;
    }
}