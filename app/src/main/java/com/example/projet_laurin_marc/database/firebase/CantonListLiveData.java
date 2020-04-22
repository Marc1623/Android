package com.example.projet_laurin_marc.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.projet_laurin_marc.database.entity.Canton;
import com.example.projet_laurin_marc.database.entity.County;
import com.example.projet_laurin_marc.database.pojo.CantonWithCounties;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CantonListLiveData extends LiveData<List<CantonWithCounties>> {
    private static final String TAG = "CantonLiveData";

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public CantonListLiveData(DatabaseReference ref) {
        reference = ref;
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(toCantonWithCountyList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<CantonWithCounties> toCantonWithCountyList(DataSnapshot snapshot) {
        List<CantonWithCounties> cantons = new ArrayList<>();

        for (DataSnapshot childCanton : snapshot.getChildren()) {
            CantonWithCounties cantonWithCounties = new CantonWithCounties();

            Canton entity = childCanton.getValue(Canton.class);
            entity.setId(childCanton.getKey());
            cantonWithCounties.canton = entity;

            County county = childCanton.child("counties").getValue(County.class);
            cantonWithCounties.counties.add(county);
            cantons.add(cantonWithCounties);
        }
        return cantons;
    }

    private List<Canton> toCantonList(DataSnapshot snapshot) {
        List<Canton> cantons = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            Canton entity = childSnapshot.getValue(Canton.class);
            entity.setId(childSnapshot.getKey());
            cantons.add(entity);
        }
        return cantons;
    }
}
