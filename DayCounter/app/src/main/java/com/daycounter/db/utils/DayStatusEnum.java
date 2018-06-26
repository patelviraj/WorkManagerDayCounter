package com.daycounter.db.utils;

public enum DayStatusEnum {

    FUTURE(1),
    PRESENT(2),
    SKIP(3),
    COMPLETED(4);

    private int code;

    DayStatusEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static DayStatusEnum fromCode(int code) {
        for (DayStatusEnum f : values()) {
            if (f.getCode() == code) {
                return f;
            }
        }
        return DayStatusEnum.FUTURE;
    }
}
