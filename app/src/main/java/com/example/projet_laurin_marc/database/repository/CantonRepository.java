package com.example.projet_laurin_marc.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.projet_laurin_marc.BaseApp;
import com.example.projet_laurin_marc.database.async.canton.CreateCanton;
import com.example.projet_laurin_marc.database.async.canton.DeleteCanton;
import com.example.projet_laurin_marc.database.async.canton.UpdateCanton;

import com.example.projet_laurin_marc.database.entity.Canton;
import com.example.projet_laurin_marc.database.entity.County;
import com.example.projet_laurin_marc.database.util.OnAsyncEventListener;

import java.util.List;

public class CantonRepository {

    private static CantonRepository instance;

    private CantonRepository() {
    }

    public static CantonRepository getInstance() {
        if (instance == null) {
            synchronized (County.class) {
                if (instance == null) {
                    instance = new CantonRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<Canton> getCanton(final String cantonId, Application application) {
        return ((BaseApp) application).getDatabase().cantonDao().getById(cantonId);
    }

    public void insert(final Canton canton, OnAsyncEventListener callback,
                       Application application) {
        new CreateCanton(application, callback).execute(canton);
    }

    public void insertAllCantons(final List<Canton> cantons, OnAsyncEventListener callback,
                                 Application application) {
        CreateCanton creator = new CreateCanton(application, callback);
        for (int i = 0; i < cantons.size(); i++) {
            creator.execute(cantons.get(i));
        }
    }

    public void update(final Canton canton, OnAsyncEventListener callback,
                       Application application) {
        new UpdateCanton(application, callback).execute(canton);
    }

    public void delete(final Canton canton, OnAsyncEventListener callback,
                       Application application) {
        new DeleteCanton(application, callback).execute(canton);
    }

    public LiveData<List<Canton>> getAllCantons(Application application) {
        return ((BaseApp) application).getDatabase().cantonDao().getAll();
    }
}
