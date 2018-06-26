package com.daycounter.db.utils;

import android.arch.persistence.room.TypeConverter;

import static com.daycounter.db.utils.DayStatusEnum.COMPLETED;
import static com.daycounter.db.utils.DayStatusEnum.FUTURE;
import static com.daycounter.db.utils.DayStatusEnum.PRESENT;
import static com.daycounter.db.utils.DayStatusEnum.SKIP;

public class DayStatusConverter {

    @TypeConverter
    public static DayStatusEnum toStatus(int status) {
        if (status == FUTURE.getCode()) {
            return FUTURE;
        } else if (status == PRESENT.getCode()) {
            return PRESENT;
        } else if (status == SKIP.getCode()) {
            return SKIP;
        } else if (status == COMPLETED.getCode()) {
            return COMPLETED;
        } else {
            throw new IllegalArgumentException("Could not recognize status");
        }
    }

    @TypeConverter
    public static Integer toInteger(DayStatusEnum status) {
        return status.getCode();
    }
}
