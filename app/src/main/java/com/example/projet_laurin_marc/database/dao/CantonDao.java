package com.example.projet_laurin_marc.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projet_laurin_marc.database.entity.Canton;
import java.util.List;

@Dao
public interface CantonDao {

    @Query("SELECT * FROM canton WHERE name = :id")
    LiveData<Canton> getById(String id);

    @Query("SELECT * from canton")
    LiveData<List<Canton>> getAll();

    @Insert
    void insert(Canton canton) throws SQLiteConstraintException;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Canton> cantons);

    @Update
    void update(Canton canton);

    @Delete
    void delete(Canton canton);

    @Query("DELETE FROM canton")
    void deleteAll();

}
