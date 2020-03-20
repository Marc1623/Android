package com.example.projet_laurin_marc.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.projet_laurin_marc.database.dao.AddressDao;
import com.example.projet_laurin_marc.database.dao.UserDao;
import com.example.projet_laurin_marc.database.entity.Address;
import com.example.projet_laurin_marc.database.entity.User;

@Database(entities = {Address.class},version = 1)
public abstract class AddressDatabase extends RoomDatabase {

    private static AddressDatabase instance;
    public abstract AddressDao addressDao();


    //singletone pattern!
    public static synchronized AddressDatabase getInstance(Context context){
        if (instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(), AddressDatabase.class, "address_database").
                    fallbackToDestructiveMigration().addCallback(userCallback).build();
        }
        return instance;
    }

    private static Callback userCallback = new Callback(){
      @Override
      public void onCreate(@NonNull SupportSQLiteDatabase db){
          super.onCreate(db);
          new AddressDatabase.PopulateDbAsyncTask(instance).execute();
      }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private AddressDao addressDao;

        private PopulateDbAsyncTask(AddressDatabase db){
            addressDao = db.addressDao();
        }

        @Override
        protected Void doInBackground(Void... voids){
            addressDao.insert(new Address());
            return null;
        }
    }

}
