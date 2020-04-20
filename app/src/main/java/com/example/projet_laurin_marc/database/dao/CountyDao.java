package com.example.projet_laurin_marc.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projet_laurin_marc.database.entity.County;

import java.util.List;

@Dao
public interface CountyDao {

    @Query("SELECT * FROM county WHERE name = :id")
    LiveData<County> getById(String id);

    @Query("SELECT * from county")
    LiveData<List<County>> getAll();

    @Query("SELECT * from county  WHERE canton = :canton")
    LiveData<List<County>> getAllByCanton(String canton);

    @Insert
    void insert(County county) throws SQLiteConstraintException;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<County> counties);

    @Update
    void update(County county);

    @Delete
    void delete(County county);

    @Query("DELETE FROM county")
    void deleteAll();

}
