package com.example.projet_laurin_marc.database.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.projet_laurin_marc.database.entity.Address;
import com.example.projet_laurin_marc.database.repository.AddressRepository;

import java.util.List;

public class AddressViewModel extends AndroidViewModel {
    private AddressRepository addressRepository;
    private LiveData<List<Address>> addresses;

    public AddressViewModel(@NonNull Application application) {
        super(application);

        addressRepository = new AddressRepository(application);
        addresses = addressRepository.getAllAddresses();
    }

    public void insert(Address address){
        addressRepository.insert(address);
    }

    public void update(Address address){
        addressRepository.update(address);
    }

    public void delete(Address address){
        addressRepository.delete(address);
    }
}
