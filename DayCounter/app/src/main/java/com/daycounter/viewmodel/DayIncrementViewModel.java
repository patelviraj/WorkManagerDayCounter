package com.daycounter.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.daycounter.db.AppDatabase;
import com.daycounter.db.utils.DayStatusEnum;
import com.daycounter.utils.AppConstants;
import com.daycounter.utils.Logger;
import com.daycounter.utils.SessionManager;
import com.daycounter.worker.DayIncrementWorker;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

public class DayIncrementViewModel extends AndroidViewModel {

    private WorkManager mWorkManager;
    private AppDatabase appDatabase;

    public DayIncrementViewModel(@NonNull Application application) {
        super(application);
        mWorkManager = WorkManager.getInstance();

        appDatabase = AppDatabase.getAppDatabase(this.getApplication());
    }

    public void setStatusToPresentForFirstDay() {
        appDatabase.daysDao().updateStatus(1, DayStatusEnum.PRESENT.getCode());
    }

    public void setDayIncrementWorker() {

        /*PeriodicWorkRequest.Builder dayWorkBuilder =
                new PeriodicWorkRequest.Builder(DayIncrementWorker.class, 15, TimeUnit.MINUTES, 5,
                        TimeUnit.MINUTES);*/
        PeriodicWorkRequest.Builder dayWorkBuilder =
                new PeriodicWorkRequest.Builder(DayIncrementWorker.class, 24, TimeUnit.HOURS, 5,
                        TimeUnit.MINUTES);


        // Add Tag to workBuilder
        dayWorkBuilder.addTag(AppConstants.DAY_INCREMENT_WORK_NAME);
        // Create the actual work object:
        PeriodicWorkRequest dayWork = dayWorkBuilder.build();
        // Then enqueue the recurring task:
        mWorkManager.enqueue(dayWork);

        // Set UUID in session for future use
        UUID workerId = dayWork.getId();
        SessionManager session = new SessionManager(this.getApplication());
        session.setStringDataByKey(SessionManager.KEY_WORKER_UUID, workerId.toString());
        Logger.e("Save Worker UUID", workerId.toString());
    }

    /**
     * Cancel work using the work's unique id
     */
    public void cancelWorkByUUID() {
        SessionManager session = new SessionManager(this.getApplication());
        String workerUuid = session.getStringDataByKey(SessionManager.KEY_WORKER_UUID);
        Logger.e("Canceling Worker UUID", workerUuid);
        if (workerUuid != null && workerUuid.length() > 0) {
            mWorkManager.cancelWorkById(UUID.fromString(workerUuid));
            mWorkManager.pruneWork();
        }
        session.setStringDataByKey(SessionManager.KEY_WORKER_UUID, "");
    }

    /**
     * Cancel work using the work's unique name
     */
    void cancelWorkByTag(String workerUdi) {
        mWorkManager.cancelAllWorkByTag(AppConstants.DAY_INCREMENT_WORK_NAME);
    }
}
