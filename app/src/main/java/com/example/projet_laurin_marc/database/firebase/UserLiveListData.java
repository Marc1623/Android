package com.example.projet_laurin_marc.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.projet_laurin_marc.database.entity.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserLiveListData extends LiveData<List<User>> {
    private static final String TAG = "UserLiveData";

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public UserLiveListData(DatabaseReference ref) {
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
            setValue(toUserList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<User> toUserList(DataSnapshot snapshot) {
        List<User> users = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            User entity = childSnapshot.child("users").getValue(User.class);
            //entity.setRowId(Integer.valueOf(childSnapshot.getKey()));
            users.add(entity);
        }
        return users;
    }
}
