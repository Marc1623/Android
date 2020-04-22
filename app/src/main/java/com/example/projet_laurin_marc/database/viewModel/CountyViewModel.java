package com.example.projet_laurin_marc.database.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.projet_laurin_marc.database.entity.Canton;
import com.example.projet_laurin_marc.database.entity.County;
import com.example.projet_laurin_marc.database.repository.CountyRepository;

import java.util.List;

public class CountyViewModel extends AndroidViewModel {
    private CountyRepository countyRepository;

    private final MediatorLiveData<List<County>> observableCounties;

    public CountyViewModel(@NonNull Application application) {
        super(application);
        observableCounties = new MediatorLiveData<>();
        observableCounties.setValue(null);

        countyRepository = new CountyRepository(application);

        LiveData<List<County>> allCounties = countyRepository.getAllCounties();
        observableCounties.addSource(allCounties, observableCounties::setValue);
    }

    public void insert(County county, Canton canton){
        countyRepository.insert(county, canton);
    }

    public void update(County county){
        countyRepository.update(county);
    }

    public void delete(County county){
        countyRepository.delete(county);
    }

    public LiveData<List<County>> getCounties() {
        return observableCounties;
    }
}
