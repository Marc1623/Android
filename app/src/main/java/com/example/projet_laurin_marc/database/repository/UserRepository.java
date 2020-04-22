package com.example.projet_laurin_marc.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.projet_laurin_marc.database.entity.User;
import com.example.projet_laurin_marc.database.firebase.UserLiveData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class UserRepository {

    private LiveData<List<User>> allUsers;

    public UserRepository(Application app) {
        allUsers = getAllUsers();
    }

    public void insert(User user) {
        String id = FirebaseDatabase.getInstance().getReference("users").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(id)
                .setValue(user);
    }

    public void update(User user) {
        System.out.println(user.getId());
        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(user.getId())
                .updateChildren(user.toMap());
    }

    public void delete(User user) {
        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(String.valueOf(user.getId()))
                .child("user")
                .removeValue();
    }

    public LiveData<List<User>> getAllUsers() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("users");
        return new UserLiveData(reference);
    }
}
