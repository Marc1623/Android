package com.example.projet_laurin_marc.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.projet_laurin_marc.database.entity.Canton;
import com.example.projet_laurin_marc.database.entity.County;
import com.example.projet_laurin_marc.database.firebase.CantonLiveData;
import com.example.projet_laurin_marc.database.firebase.CountyLiveData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CountyRepository {

    private LiveData<List<County>> allCounties;

    public CountyRepository(Application app) {
        allCounties = getAllCounties();
    }

    public void insert(County county, Canton canton) {
        System.out.println(county.getName());
        String id = FirebaseDatabase.getInstance().getReference().push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("cantons")
                .child(canton.getId())
                .child(id)
                .setValue(county);
    }

    public void update(County county) {
        FirebaseDatabase.getInstance()
                .getReference("cantons")
                .child(county.getId())
                .updateChildren(county.toMap());
    }

    public void delete(County county) {
        FirebaseDatabase.getInstance()
                .getReference("cantons")
                .child(county.getId())
                .removeValue();
    }

    public LiveData<List<County>> getAllCounties() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("cantons");
        return new CountyLiveData(reference);
    }
}
