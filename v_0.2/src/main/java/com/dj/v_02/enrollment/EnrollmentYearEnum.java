package com.dj.v_02.enrollment;

import lombok.Getter;

import java.util.Objects;

@Getter
public enum EnrollmentYearEnum {
    YEAR_2024("2024"), YEAR_2025("2025"), YEAR_2026("2026"), YEAR_2027("2027");
    private final String year;

    EnrollmentYearEnum(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return year;
    }

    public static EnrollmentYearEnum fromYear(String year) {
        for (EnrollmentYearEnum value : EnrollmentYearEnum.values()) {
            if (Objects.equals(value.getYear(), String.valueOf(year))) {
                return value;
            }
        }
        throw new IllegalArgumentException("Year not found " + year);
    }
}
