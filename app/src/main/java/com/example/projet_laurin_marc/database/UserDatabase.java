package com.example.projet_laurin_marc.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.projet_laurin_marc.database.dao.UserDao;
import com.example.projet_laurin_marc.database.entity.User;

@Database(entities = {User.class},version = 3)
public abstract class UserDatabase extends RoomDatabase {

    private static UserDatabase instance;
    public abstract UserDao userDao();

    //singletone pattern!
    public static synchronized UserDatabase getInstance(Context context){
        if (instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class, "user_database").
                    fallbackToDestructiveMigration().addCallback(userCallback).build();
        }
        return instance;
    }

    private static Callback userCallback = new Callback(){
      @Override
      public void onCreate(@NonNull SupportSQLiteDatabase db){
          super.onCreate(db);
          new UserDatabase.PopulateDbAsyncTask(instance).execute();
      }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private UserDao userDao;

        private PopulateDbAsyncTask(UserDatabase db){
            userDao = db.userDao();
        }

        @Override
        protected Void doInBackground(Void... voids){
            userDao.insert(new User());
            return null;
        }
    }

}
