package com.example.projet_laurin_marc.database.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.projet_laurin_marc.database.entity.Canton;
import com.example.projet_laurin_marc.database.repository.CantonRepository;

import java.util.List;

public class CantonViewModel extends AndroidViewModel {
    private CantonRepository cantonRepository;

    private final MediatorLiveData<List<Canton>> observableCantons;

    public CantonViewModel(@NonNull Application application) {
        super(application);
        observableCantons = new MediatorLiveData<>();
        observableCantons.setValue(null);

        cantonRepository = new CantonRepository(application);

        LiveData<List<Canton>> allCantons = cantonRepository.getAllCantons();
        observableCantons.addSource(allCantons, observableCantons::setValue);
    }

    public String insert(Canton canton){
        return cantonRepository.insert(canton);
    }

    public void update(Canton canton){
        cantonRepository.update(canton);
    }

    public void delete(Canton canton){
        cantonRepository.delete(canton);
    }

    public LiveData<List<Canton>> getCantons() {
        return observableCantons;
    }
}
