package com.daycounter.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.daycounter.db.AppDatabase;
import com.daycounter.db.entity.DaysTableModel;

import java.util.List;

public class DayListViewModel extends AndroidViewModel {

    private LiveData<List<DaysTableModel>> mDayList;

    public DayListViewModel(@NonNull Application application) {
        super(application);

        AppDatabase appDatabase = AppDatabase.getAppDatabase(this.getApplication());
        mDayList = appDatabase.daysDao().getAllDays();
    }

    public LiveData<List<DaysTableModel>> getDayList() {
        return mDayList;
    }
}
