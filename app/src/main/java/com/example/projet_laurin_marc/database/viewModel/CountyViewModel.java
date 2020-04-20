package com.example.projet_laurin_marc.database.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.projet_laurin_marc.BaseApp;

import com.example.projet_laurin_marc.database.entity.County;
import com.example.projet_laurin_marc.database.entity.User;
import com.example.projet_laurin_marc.database.repository.CountyRepository;
import com.example.projet_laurin_marc.database.util.OnAsyncEventListener;

import java.util.List;


public class CountyViewModel extends AndroidViewModel {
    private CountyRepository repository;

    private Application application;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<County> observableCounty;
    private final MediatorLiveData<List<County>> observableCounties;
    private final MediatorLiveData<List<County>> observableCountiesByCanton;

    public CountyViewModel(@NonNull Application application,
                           final String countyId, final String canton, CountyRepository countyRepository) {
        super(application);

        this.application = application;

        repository = countyRepository;

        observableCounty = new MediatorLiveData<>();
        observableCounties = new MediatorLiveData<>();
        observableCountiesByCanton = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableCounty.setValue(null);
        observableCounties.setValue(null);
        observableCountiesByCanton.setValue(null);

        LiveData<County> county = repository.getCounty(countyId, application);
        LiveData<List<County>> counties = repository.getAllCounties(application);
        LiveData<List<County>> countiesByCanton = repository.getCountiesByCanton(canton, application);

        // observe the changes of the client entity from the database and forward them
        observableCounty.addSource(county, observableCounty::setValue);
        observableCounties.addSource(counties, observableCounties::setValue);
        observableCountiesByCanton.addSource(countiesByCanton, observableCountiesByCanton::setValue);
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<County> getCounty() {
        return observableCounty;
    }

    public LiveData<List<County>> getCounties() {
        return observableCounties;
    }

    public LiveData<List<County>> getCountiesByCanton() {
        return observableCountiesByCanton;
    }

    public void createCounty(County county, OnAsyncEventListener callback) {
        repository.insert(county, callback, application);
    }

    public void updateCounty(County county, OnAsyncEventListener callback) {
        repository.update(county, callback, application);
    }

    public void deleteCounty(County county, OnAsyncEventListener callback) {
        repository.delete(county, callback, application);
    }

    public void insertAllCounties(List<County> counties, OnAsyncEventListener callback) {
        repository.insertAllCounties(counties, callback, application);
    }
}
