/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.daycounter.db.utils;

import android.os.AsyncTask;

import com.daycounter.db.AppDatabase;
import com.daycounter.db.entity.DaysTableModel;
import com.daycounter.interfaces.OnAsyncTaskEventListener;
import com.daycounter.utils.Logger;

import java.util.ArrayList;

public class DatabaseInitializer {

    public static void populateAsync(final AppDatabase db, ArrayList<DaysTableModel> daysList, OnAsyncTaskEventListener<String> listener) {

        PopulateDbAsync task = new PopulateDbAsync(db, daysList, listener);
        task.execute();
    }

    private static void addDay(final AppDatabase db, final DaysTableModel daysModel) {

        db.daysDao().insert(daysModel);
    }

    private static void populateInitialData(AppDatabase db, ArrayList<DaysTableModel> daysArray) {
        db.daysDao().deleteAll();

        // Insert Data in Database
        for (int i = 0; i < daysArray.size(); i++) {
            DaysTableModel daysModel = daysArray.get(i);
            addDay(db, daysModel);
        }
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;
        ArrayList<DaysTableModel> mDaysData = new ArrayList<>();
        OnAsyncTaskEventListener<String> mCallBack;

        PopulateDbAsync(AppDatabase db, ArrayList<DaysTableModel> daysArray, OnAsyncTaskEventListener<String> listener) {
            mDb = db;
            mDaysData = daysArray;
            mCallBack = listener;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateInitialData(mDb, mDaysData);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (mCallBack != null) {
                mCallBack.onSuccess("success");
            }
            Logger.e("Data Inserted");
        }
    }
}
