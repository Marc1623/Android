package com.example.projet_laurin_marc.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.projet_laurin_marc.database.dao.PersonDao;
import com.example.projet_laurin_marc.database.entity.Person;

@Database(entities = {Person.class},version = 5)
public abstract class PersonDatabase extends RoomDatabase {

    private static PersonDatabase instance;
    public abstract PersonDao personDao();

    //singletone pattern!
    public static synchronized PersonDatabase getInstance(Context context){
        if (instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(), PersonDatabase.class, "person_database").
                    fallbackToDestructiveMigration().addCallback(userCallback).build();
        }
        return instance;
    }

    private static Callback userCallback = new Callback(){
      @Override
      public void onCreate(@NonNull SupportSQLiteDatabase db){
          super.onCreate(db);
          new PersonDatabase.PopulateDbAsyncTask(instance).execute();
      }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private PersonDao personDao;

        private PopulateDbAsyncTask(PersonDatabase db){
            personDao = db.personDao();

        }

        @Override
        protected Void doInBackground(Void... voids){
            personDao.insert(new Person());
            return null;
        }
    }

}
