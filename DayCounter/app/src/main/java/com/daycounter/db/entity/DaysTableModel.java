package com.daycounter.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.daycounter.db.utils.DayStatusEnum;
import com.daycounter.db.utils.DayStatusConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@Entity(tableName = "days_master")
public class DaysTableModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("title")
    @Expose
    @ColumnInfo(name = "title")
    private String title = "";

    @SerializedName("day")
    @Expose
    @ColumnInfo(name = "day")
    private int day;

    @SerializedName("desc")
    @Expose
    @ColumnInfo(name = "description")
    private String description = "";

    @TypeConverters(DayStatusConverter.class)
    @ColumnInfo(name = "status")    // C = Completed / S = Skipped / P = Present / F = Future
    public int dayStatus = DayStatusEnum.FUTURE.getCode();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDayStatus() {
        return dayStatus == 0 ? DayStatusEnum.FUTURE.getCode() : dayStatus;
    }

    public void setDayStatus(int dayStatus) {
        this.dayStatus = (dayStatus == 0 ? DayStatusEnum.FUTURE.getCode() : dayStatus);
    }


    @Override
    public String toString() {
        return "DaysTableModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", day=" + day +
                ", description='" + description + '\'' +
                ", dayStatus=" + dayStatus +
                '}';
    }
}
