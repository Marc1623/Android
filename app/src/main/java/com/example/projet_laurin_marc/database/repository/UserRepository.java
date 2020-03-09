package com.example.projet_laurin_marc.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Database;

import com.example.projet_laurin_marc.database.UserDatabase;
import com.example.projet_laurin_marc.database.dao.UserDao;
import com.example.projet_laurin_marc.database.entity.User;

import java.util.List;

public class UserRepository {

    private UserDao userDao;
    private LiveData <List<User>> userslive;

    public UserRepository(Application app){
        UserDatabase database = UserDatabase.getInstance(app);
        userDao = database.userDao();
        userslive = userDao.getAll();
    }

    public void insert(User user){
        new InsertUserAsyncTask(userDao).execute(user);
    }

    public LiveData<List<User>> getAllUser() {
        return userslive;
    }

    private static class InsertUserAsyncTask extends AsyncTask <User, Void, Void>{
        private UserDao userD;

        public InsertUserAsyncTask(UserDao udao){
            this.userD = udao;
        }

        @Override
       protected Void doInBackground(User... users){
            userD.insert(users[0]);
            return null;
        }
   }


}
