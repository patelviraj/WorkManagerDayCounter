package com.daycounter.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.daycounter.R;
import com.daycounter.databinding.ActivityMainBinding;
import com.daycounter.db.AppDatabase;
import com.daycounter.db.utils.DatabaseInitializer;
import com.daycounter.db.utils.ParseLocalJSONFile;
import com.daycounter.interfaces.OnAsyncTaskEventListener;
import com.daycounter.ui.BaseActivity;
import com.daycounter.ui.fragment.FragmentDayList;
import com.daycounter.utils.Logger;
import com.daycounter.utils.SessionManager;
import com.daycounter.viewmodel.DayIncrementViewModel;

public class MainActivity extends BaseActivity {

    private DayIncrementViewModel mDayIncrementViewModel;

    ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mDayIncrementViewModel = ViewModelProviders.of(MainActivity.this).get(DayIncrementViewModel.class);

        // If database is not created then create database
        if (AppDatabase.getAppDatabase(this).daysDao().countDays() == 0) {

            // Display Progress
            showProgressBar().show();

            ParseLocalJSONFile objJsonRead = new ParseLocalJSONFile();
            DatabaseInitializer.populateAsync(AppDatabase.getAppDatabase(this), objJsonRead.getDaysList(this), new OnAsyncTaskEventListener<String>() {
                @Override
                public void onSuccess(String object) {

                    // Set Day Increment Worker Service
                    setWorkerService();

                    // Set First Day as Present when Data is inserted in database
                    mDayIncrementViewModel.setStatusToPresentForFirstDay();

                    prepareLayout();
                    //  Hide Progress
                    hideProgressBar();
                }

                @Override
                public void onFailure(Exception e) {
                    // Hide Progress
                    hideProgressBar();
                }
            });
        } else {
            if (session.getStringDataByKey(SessionManager.KEY_WORKER_UUID).equals("") && !session.getBooleanDataByKey(SessionManager.KEY_IS_COMPLETED)) {
                // Set Day Increment Worker Service
                setWorkerService();
                // Set First Day as Present when Data is inserted in database
                mDayIncrementViewModel.setStatusToPresentForFirstDay();
            } else {
                Logger.e("Stored Worker UUID", session.getStringDataByKey(SessionManager.KEY_WORKER_UUID));
            }
            prepareLayout();
        }
    }

    private void setWorkerService() {
        Logger.e("SetWorkerService", "SetWorkerService");
        session.setStringDataByKey(SessionManager.KEY_CURRENT_DAY, "1");
        mDayIncrementViewModel.setDayIncrementWorker();
    }

    private void prepareLayout() {

        String day = session.getStringDataByKey(SessionManager.KEY_CURRENT_DAY);
        Logger.e("Day Value in Session", day);
        if (day != null && day.length() > 0) {
            int dayNumber = Integer.parseInt(day);
            // If All days are completed then cancel background work
            if (dayNumber >= 31) {
                mDayIncrementViewModel.cancelWorkByUUID();
            }
        }

        pushFragments(new FragmentDayList());
    }

    public void pushFragments(Fragment fragment) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        ft.replace(R.id.realtabcontent, fragment);
        ft.commit();
    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }
}
