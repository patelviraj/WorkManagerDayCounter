package com.daycounter.db.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;

import com.daycounter.db.entity.DaysTableModel;

import java.util.List;

@Dao
public interface DaysDao {

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM days_master")
    LiveData<List<DaysTableModel>> getAllDays();

    @Query("SELECT COUNT(*) from days_master")
    int countDays();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DaysTableModel day);

    @Query("DELETE FROM days_master")
    void deleteAll();

    @Query("UPDATE days_master SET status = :status where id LIKE  :dayId")
    void updateStatus(int dayId, int status);

    @Query("UPDATE days_master SET status = :status where id LIKE :dayId")
    void updateStatusFromWorker(int dayId, int status);

    @Query("UPDATE days_master SET status = 3 WHERE day < :dayId AND status != 4 AND status != 3 AND day !=:dayId")
    void updateStatusFromWorker(int dayId);
}
