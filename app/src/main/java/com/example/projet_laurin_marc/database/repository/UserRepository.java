package com.example.projet_laurin_marc.database.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.projet_laurin_marc.database.entity.User;
import com.example.projet_laurin_marc.database.firebase.UserLiveData;
import com.example.projet_laurin_marc.util.OnAsyncEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class UserRepository {

    private LiveData<List<User>> allUsers;

    public UserRepository(Application app) {
        allUsers = getAllUsers();
    }


    public void signIn(final String email, final String password,
                       final OnCompleteListener<AuthResult> listener) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(listener);
    }

    public void register(final User user) {
        System.out.println("register: UserRepo");
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                user.getEmail(),
                user.getPwd()
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                user.setId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                System.out.println("register: before insert");
                insert(user);
            } else {

            }
        });
    }

    private void insert(final User user) {
        System.out.println("insert: int insert");
        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(user);
    }


   /* public void insert(User user) {

        String id = FirebaseDatabase.getInstance().getReference("users").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(id)
                .setValue(user);

    }*/

    public void update(User user) {
        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(user.getId())
                .updateChildren(user.toMap());
    }

    public void delete(User user) {
        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(user.getId())
                .removeValue();
    }

    public LiveData<List<User>> getAllUsers() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("users");
        return new UserLiveData(reference);
    }
}
