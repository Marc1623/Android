package com.example.projet_laurin_marc.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projet_laurin_marc.database.entity.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user WHERE email = :id")
    LiveData<User> getById(String id);

    @Query("SELECT * from user")
    LiveData<List<User>> getAll();

    @Query("SELECT * from user WHERE county = :county")
    LiveData<List<User>> getAllByCounty(String county);

    @Insert
    void insert(User user) throws SQLiteConstraintException;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<User> users);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM user")
    void deleteAll();

}
