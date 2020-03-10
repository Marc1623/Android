package com.example.projet_laurin_marc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projet_laurin_marc.database.entity.Person;
import com.example.projet_laurin_marc.database.entity.User;

import java.util.List;

@Dao
public interface PersonDao {
    @Insert
    void insert(Person person);

    @Update
    void update(Person... person);

    @Delete
    void delete(Person person);

    @Query("Select * From  person")
    LiveData<List<Person>> getAll();

}
