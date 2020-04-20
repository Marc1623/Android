package com.example.projet_laurin_marc.database.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.projet_laurin_marc.database.entity.Canton;
import com.example.projet_laurin_marc.database.entity.County;
import com.example.projet_laurin_marc.database.repository.CantonRepository;
import com.example.projet_laurin_marc.database.util.OnAsyncEventListener;

import java.util.List;


public class CantonViewModel extends AndroidViewModel {
    private CantonRepository repository;

    private Application application;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<Canton> observableCanton;
    private final MediatorLiveData<List<Canton>> observableCantons;

    public CantonViewModel(@NonNull Application application,
                           final String cantonId, CantonRepository cantonRepository) {
        super(application);

        this.application = application;

        repository = cantonRepository;

        observableCanton = new MediatorLiveData<>();
        observableCantons = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableCanton.setValue(null);
        observableCantons.setValue(null);

        LiveData<Canton> canton = repository.getCanton(cantonId, application);
        LiveData<List<Canton>> cantons = repository.getAllCantons( application);

        // observe the changes of the client entity from the database and forward them
        observableCanton.addSource(canton, observableCanton::setValue);
        observableCantons.addSource(cantons, observableCantons::setValue);
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<Canton> getCanton() {
        return observableCanton;
    }

    public LiveData<Canton> getCantons() {
        return observableCanton;
    }

    public void createCanton(Canton canton, OnAsyncEventListener callback) {
        repository.insert(canton, callback, application);
    }

    public void updateCanton(Canton canton, OnAsyncEventListener callback) {
        repository.update(canton, callback, application);
    }

    public void deleteCanton(Canton canton, OnAsyncEventListener callback) {
        repository.delete(canton, callback, application);
    }

    public void insertAllCantons(List<Canton> cantons, OnAsyncEventListener callback) {
        repository.insertAllCantons(cantons, callback, application);
    }
}
