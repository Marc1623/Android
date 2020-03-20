package com.example.projet_laurin_marc.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

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

    public void update(User user){
        new UpdateUserAsyncTask(userDao).execute(user);
    }

    public void delete(User user){
        new DeleteUserAsyncTask(userDao).execute(user);
    }

    public LiveData<List<User>> getAllUsers() {
        return userslive;
    }

    private static class InsertUserAsyncTask extends AsyncTask <User, Void, Void> {
        private UserDao userD;

        public InsertUserAsyncTask(UserDao udao) {
            this.userD = udao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userD.insert(users[0]);
            return null;
        }
    }

        private static class UpdateUserAsyncTask extends AsyncTask <User, Void, Void>{
            private UserDao userDao;

            public UpdateUserAsyncTask(UserDao userDao){
                this.userDao = userDao;
            }

            @Override
            protected Void doInBackground(User... users){
                try{
                    for(User user : users){
                        userDao.update(user);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }
        }

    private static class DeleteUserAsyncTask extends AsyncTask <User, Void, Void>{
        private UserDao userDao;

        public DeleteUserAsyncTask(UserDao userDao){
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users){
            try{
                for(User user : users){
                    userDao.delete(user);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
}
