package com.example.projet_laurin_marc.database.viewModel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.projet_laurin_marc.BaseApp;
import com.example.projet_laurin_marc.database.entity.User;
import com.example.projet_laurin_marc.database.repository.UserRepository;
import com.example.projet_laurin_marc.database.util.OnAsyncEventListener;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository repository;

    private Application application;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<User> observableUser;
    private final MediatorLiveData<List<User>> observableUsers;
    private final MediatorLiveData<List<User>> observableUsersByCounty;

    public UserViewModel(@NonNull Application application,
                           final String userId, final String county, UserRepository userRepository) {
        super(application);

        this.application = application;

        repository = userRepository;

        observableUser = new MediatorLiveData<>();
        observableUsers = new MediatorLiveData<>();
        observableUsersByCounty = new MediatorLiveData<>();

        // set by default null, until we get data from the database.
        observableUser.setValue(null);
        observableUsers.setValue(null);
        observableUsersByCounty.setValue(null);

        LiveData<User> user = repository.getUser(userId, application);
        LiveData<List<User>> users = repository.getAllUsers(application);
        LiveData<List<User>> usersByCounty = repository.getAllUsersByCounty(county, application);

        // observe the changes of the client entity from the database and forward them
        observableUser.addSource(user, observableUser::setValue);
        observableUsers.addSource(users, observableUsers::setValue);
        observableUsersByCounty.addSource(usersByCounty, observableUsersByCounty::setValue);
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<User> getUser() {
        return observableUser;
    }

    public LiveData<List<User>> getAllUsers() {
        return observableUsers;
    }

    public LiveData<List<User>> getAllUsersByCounty() {
        return observableUsersByCounty;
    }

    public void createUser(User user, OnAsyncEventListener callback) {
        repository.insert(user, callback, application);
    }

    public void updateUser(User user, OnAsyncEventListener callback) {
        repository.update(user, callback, application);
    }

    public void deleteUser(User user, OnAsyncEventListener callback) {
        repository.delete(user, callback, application);

    }


}
