package com.example.projet_laurin_marc;

import android.app.Application;

import com.example.projet_laurin_marc.database.AppDatabase;
import com.example.projet_laurin_marc.database.repository.CantonRepository;
import com.example.projet_laurin_marc.database.repository.CountyRepository;
import com.example.projet_laurin_marc.database.repository.PersonRepository;
import com.example.projet_laurin_marc.database.repository.UserRepository;

public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }


    public CantonRepository getCantonRepository() {
        return CantonRepository.getInstance();
    }

    public CountyRepository getCountyRepository() {
        return CountyRepository.getInstance();
    }

    public PersonRepository getPersonRepository() {
        return PersonRepository.getInstance();
    }

    public UserRepository getUserRepository() {
        return UserRepository.getInstance();
    }
}
