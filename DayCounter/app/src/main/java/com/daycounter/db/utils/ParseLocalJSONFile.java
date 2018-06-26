package com.daycounter.db.utils;

import android.content.Context;

import com.daycounter.db.entity.DaysTableModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ParseLocalJSONFile {

    private String loadJSONFromAsset(Context mContext) {
        String json;
        try {
            InputStream is = mContext.getAssets().open("days_data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public ArrayList<DaysTableModel> getDaysList(Context mContext) {

        ArrayList<DaysTableModel> daysList = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(mContext));
            JSONArray daysArray = obj.getJSONArray("day_list");

            Gson gson = new Gson();
            daysList = gson.fromJson(daysArray.toString(),
                    new TypeToken<ArrayList<DaysTableModel>>() {
                    }.getType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return daysList;
    }
}
