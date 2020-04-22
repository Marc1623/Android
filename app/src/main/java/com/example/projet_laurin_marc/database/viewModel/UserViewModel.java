package com.example.projet_laurin_marc.database.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.projet_laurin_marc.database.entity.User;
import com.example.projet_laurin_marc.database.repository.UserRepository;
import com.example.projet_laurin_marc.util.OnAsyncEventListener;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository userRepository;

    private final MediatorLiveData<List<User>> observableUsers;

    public UserViewModel(@NonNull Application application) {
        super(application);
        observableUsers = new MediatorLiveData<>();
        observableUsers.setValue(null);

        userRepository = new UserRepository(application);

        LiveData<List<User>> allUsers = userRepository.getAllUsers();
        observableUsers.addSource(allUsers, observableUsers::setValue);
    }
/*
    public void insert(User user ){
        userRepository.insert(user , new OnAsyncEventListener);
    }*/

    public void update(User user){
        userRepository.update(user);
    }

    public void delete(User user){
        userRepository.delete(user);
    }

    public LiveData<List<User>> getUsers() {
        return observableUsers;
    }
}
