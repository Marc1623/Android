package com.example.projet_laurin_marc.database.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.projet_laurin_marc.database.entity.User;
import com.example.projet_laurin_marc.database.repository.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private LiveData<List<User>> users;

    public UserViewModel(@NonNull Application application) {
        super(application);

        userRepository = new UserRepository(application);
        users = userRepository.getAllUsers();
    }

    public void insert(User user){
        userRepository.insert(user);
    }

    public void update(User user){
        userRepository.update(user);
    }

    public void delete(User user){
        userRepository.delete(user);
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }
}
