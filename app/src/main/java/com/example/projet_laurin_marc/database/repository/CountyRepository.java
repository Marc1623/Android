package com.example.projet_laurin_marc.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.projet_laurin_marc.BaseApp;
import com.example.projet_laurin_marc.database.async.county.CreateCounty;
import com.example.projet_laurin_marc.database.async.county.DeleteCounty;
import com.example.projet_laurin_marc.database.async.county.UpdateCounty;

import com.example.projet_laurin_marc.database.entity.Canton;
import com.example.projet_laurin_marc.database.entity.County;

import com.example.projet_laurin_marc.database.entity.User;
import com.example.projet_laurin_marc.database.util.OnAsyncEventListener;

import java.util.ArrayList;
import java.util.List;

public class CountyRepository {

    private static CountyRepository instance;

    private CountyRepository() {
    }

    public static CountyRepository getInstance() {
        if (instance == null) {
            synchronized (Canton.class) {
                if (instance == null) {
                    instance = new CountyRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<County> getCounty(final String countyId, Application application) {
        return ((BaseApp) application).getDatabase().countyDao().getById(countyId);
    }

    public void insert(final County county, OnAsyncEventListener callback,
                       Application application) {
        new CreateCounty(application, callback).execute(county);
    }

    public void insertAllCounties(final List<County> counties, OnAsyncEventListener callback,
                       Application application) {
       CreateCounty creator = new CreateCounty(application, callback);
       for(int i = 0; i<counties.size(); i++){
           creator.execute(counties.get(i));
        }
    }

    public void update(final County county, OnAsyncEventListener callback,
                       Application application) {
        new UpdateCounty(application, callback).execute(county);
    }

    public void delete(final County county, OnAsyncEventListener callback,
                       Application application) {
        new DeleteCounty(application, callback).execute(county);
    }

    // ----------------------------------------------------------
    // All Counties
    public LiveData<List<County>> getAllCounties( Application application) {
        return ((BaseApp) application).getDatabase().countyDao().getAll();
    }

    public LiveData<List<County>> getCountiesByCanton(String canton, Application application) {
        return ((BaseApp) application).getDatabase().countyDao().getAllByCanton(canton);
    }
}
