package com.example.projet_laurin_marc.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.projet_laurin_marc.database.entity.Canton;
import com.example.projet_laurin_marc.database.firebase.CantonLiveData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CantonRepository {

    private LiveData<List<Canton>> allCantons;

    public CantonRepository(Application app) {
        allCantons = getAllCantons();
    }

    public String insert(Canton canton) {
        String id = FirebaseDatabase.getInstance().getReference("cantons").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("cantons")
                .child(id)
                .setValue(canton);
        return id;
    }

    public void update(Canton canton) {
        FirebaseDatabase.getInstance()
                .getReference("cantons")
                .child(canton.getId())
                .updateChildren(canton.toMap());
    }

    public void delete(Canton canton) {
        FirebaseDatabase.getInstance()
                .getReference("cantons")
                .child(canton.getId())
                .removeValue();
    }

    public LiveData<List<Canton>> getAllCantons() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("cantons");
        return new CantonLiveData(reference);
    }
}
