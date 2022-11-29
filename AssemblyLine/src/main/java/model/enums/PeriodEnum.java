package model.enums;

public enum PeriodEnum {
    MORNING_TIME_MINUTES(180),
    AFTERNOON_TIME_MINUTES(240);

    public final int minutes;

    PeriodEnum(int minutes) {
        this.minutes = minutes;
    }

    public int getMinutes() {
        return minutes;
    }
}
