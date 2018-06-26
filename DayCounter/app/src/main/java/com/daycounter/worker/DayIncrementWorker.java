package com.daycounter.worker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.daycounter.db.AppDatabase;
import com.daycounter.db.utils.DayStatusEnum;
import com.daycounter.utils.AppConstants;
import com.daycounter.utils.Logger;
import com.daycounter.utils.SessionManager;

import androidx.work.Data;
import androidx.work.Worker;

public class DayIncrementWorker extends Worker {

    private static final String TAG = DayIncrementWorker.class.getSimpleName();

    @NonNull
    @Override
    public Result doWork() {

        Context applicationContext = getApplicationContext();

        try {

            SessionManager session = new SessionManager(applicationContext);
            String storedDay = session.getStringDataByKey(SessionManager.KEY_CURRENT_DAY);
            if (TextUtils.isEmpty(storedDay)) {
                Log.e(TAG, "Invalid day");
                throw new IllegalArgumentException("Invalid day number");
            }

            int day = Integer.parseInt(storedDay);
            Logger.e("Day Before Update In Worker", String.valueOf(day));
            day++;

            // Return the output for the temp file
            setOutputData(new Data.Builder().putString(AppConstants.KEY_STORE_DAY, String.valueOf(day)).build());

            Logger.e("Update Day In Worker", String.valueOf(day));

            session.setStringDataByKey(SessionManager.KEY_CURRENT_DAY, String.valueOf(day));

            // Update Value in Database
            AppDatabase appDatabase = AppDatabase.getAppDatabase(applicationContext);
            // If day less than 30 then update in database
            if (day < 31) {
                // Change Day to Present
                appDatabase.daysDao().updateStatusFromWorker(day, DayStatusEnum.PRESENT.getCode());
                // Change previous days to Skip if not completed else remain as it is
                appDatabase.daysDao().updateStatusFromWorker(day);
            } else {
                appDatabase.daysDao().updateStatusFromWorker(day);
                session.setBooleanDataByKey(SessionManager.KEY_IS_COMPLETED, true);
            }

            // If there were no errors, return SUCCESS
            return Result.SUCCESS;

        } catch (NumberFormatException e) {
            e.printStackTrace();
            return Result.FAILURE;
        } catch (Throwable throwable) {
            // If there were errors, return FAILURE
            Log.e(TAG, "Error increasing day", throwable);
            return Result.FAILURE;
        }
    }
}
