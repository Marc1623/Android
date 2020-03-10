package com.example.projet_laurin_marc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projet_laurin_marc.database.entity.Address;
import com.example.projet_laurin_marc.database.entity.User;

import java.util.List;

@Dao
public interface AddressDao {
    @Insert
    void insert(Address address);

    @Update
    void update(Address... address);

    @Delete
    void delete(Address address);

    @Query("Select * From  address")
    LiveData<List<Address>> getAll();

}
