package com.example.projet_laurin_marc.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projet_laurin_marc.database.entity.Person;

import java.util.List;

@Dao
public interface PersonDao {

    @Query("SELECT * FROM person WHERE ahv = :id")
    LiveData<Person> getById(String id);

    @Query("SELECT * from person")
    LiveData<List<Person>> getAll();

    @Query("SELECT * from person  WHERE county = :county")
    LiveData<List<Person>> getAllByCounty(String county);

    @Insert
    void insert(Person Person) throws SQLiteConstraintException;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Person> persons);

    @Update
    void update(Person person);

    @Delete
    void delete(Person person);

    @Query("DELETE FROM person")
    void deleteAll();

}
