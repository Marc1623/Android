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

public class CantonWithCountyList extends LiveData<List<CantonWithCounties>> {
    private static final String TAG = "CantonsCountyLiveData";
    private final String owner;
    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public CantonWithCountyList(DatabaseReference ref, String owner) {
        reference = ref;
        this.owner = owner;
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
            setValue(toList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<CantonWithCounties> toList(DataSnapshot snapshot) {

        List<CantonWithCounties> cantonWithCountiesList = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            if(!childSnapshot.getKey().equals(owner)){
                CantonWithCounties cantonWithCounties = new CantonWithCounties();
                cantonWithCounties.canton = childSnapshot.getValue(Canton.class);
                cantonWithCounties.canton.setId(childSnapshot.getKey());
                cantonWithCounties.counties = toCounties(childSnapshot.child("counties"),
                        childSnapshot.getKey());
                cantonWithCountiesList.add(cantonWithCounties);
            }
        }
        return cantonWithCountiesList;
    }

    private List<County> toCounties(DataSnapshot snapshot, String ownerId) {
        List<County> counties = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            County entity = childSnapshot.getValue(County.class);
            entity.setId(childSnapshot.getKey());
            entity.setOwner(ownerId);
            counties.add(entity);
        }
        return counties;
    }
}
