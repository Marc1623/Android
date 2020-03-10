package com.example.projet_laurin_marc.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.projet_laurin_marc.database.dao.AddressDao;
import com.example.projet_laurin_marc.database.dao.PersonDao;
import com.example.projet_laurin_marc.database.dao.UserDao;
import com.example.projet_laurin_marc.database.entity.User;

@Database(entities = {User.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract UserDao userDao();

    public abstract PersonDao personDao();

    public abstract AddressDao addressDao();

    //singletone pattern!
    public static synchronized AppDatabase getInstance(Context context){
        if (instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "user_database").
                    fallbackToDestructiveMigration().addCallback(userCallback).build();
        }
        return instance;
    }

    private static RoomDatabase.Callback userCallback = new RoomDatabase.Callback(){
      @Override
      public void onCreate(@NonNull SupportSQLiteDatabase db){
          super.onCreate(db);
          new AppDatabase.PopulateDbAsyncTask(instance).execute();
      }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private UserDao userDao;

        private PopulateDbAsyncTask(AppDatabase db){
            userDao = db.userDao();
        }

        @Override
        protected Void doInBackground(Void... voids){
            userDao.insert(new User());
            return null;
        }
    }

}
