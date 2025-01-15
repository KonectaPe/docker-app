package com.dj.v_02.enrollment;

import lombok.Getter;

import java.util.Objects;

@Getter
public enum EnrollmentCycleEnum {
    CYCLE_0("0"), CYCLE_1("1"), CYCLE_2("2");
    private final String cycle;

    EnrollmentCycleEnum(String cycle) {
        this.cycle = cycle;
    }

    @Override
    public String toString() {
        return cycle;
    }

    public static EnrollmentCycleEnum fromCycle(String cycle) {
        for (EnrollmentCycleEnum value : EnrollmentCycleEnum.values()) {
            if (Objects.equals(value.getCycle(), String.valueOf(cycle))) {
                return value;
            }
        }
        throw new IllegalArgumentException("Cycle not found " + cycle);
    }
}
