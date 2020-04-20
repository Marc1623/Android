package com.example.projet_laurin_marc.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.projet_laurin_marc.BaseApp;

import com.example.projet_laurin_marc.database.async.user.CreateUser;
import com.example.projet_laurin_marc.database.async.user.DeleteUser;
import com.example.projet_laurin_marc.database.async.user.UpdateUser;

import com.example.projet_laurin_marc.database.entity.User;
import com.example.projet_laurin_marc.database.util.OnAsyncEventListener;

import java.util.List;


public class UserRepository {

    private static UserRepository instance;

    private UserRepository() {
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            synchronized (CountyRepository.class) {
                if (instance == null) {
                    instance = new UserRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<User> getUser(final String userId, Application application) {
        return ((BaseApp) application).getDatabase().userDao().getById(userId);
    }

    public void insert(final User user, OnAsyncEventListener callback,
                       Application application) {
        new CreateUser(application, callback).execute(user);
    }

    public void update(final User user, OnAsyncEventListener callback,
                       Application application) {
        new UpdateUser(application, callback).execute(user);
    }

    public void delete(final User user, OnAsyncEventListener callback,
                       Application application) {
        new DeleteUser(application, callback).execute(user);
    }

    // ----------------------------------------------------------
    // All Users
    public LiveData<List<User>> getAllUsers(Application application) {
        return ((BaseApp) application).getDatabase().userDao().getAll();
    }
    public LiveData<List<User>> getAllUsersByCounty(final String county, Application application) {
        return ((BaseApp) application).getDatabase().userDao().getAllByCounty(county);
    }
}
