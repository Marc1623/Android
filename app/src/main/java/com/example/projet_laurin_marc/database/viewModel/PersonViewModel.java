package com.example.projet_laurin_marc.database.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.projet_laurin_marc.database.entity.Person;
import com.example.projet_laurin_marc.database.repository.PersonRepository;


import java.util.List;

public class PersonViewModel extends AndroidViewModel {
    private PersonRepository personRepository;
    private LiveData<List<Person>> persons;

    public PersonViewModel(@NonNull Application application) {
        super(application);

        personRepository = new PersonRepository(application);
        persons = personRepository.getAllPersons();
    }

    public void insert(Person person){
        personRepository.insert(person);
    }

    public void update(Person person){
        personRepository.update(person);
    }

    public void delete(Person person){
        personRepository.delete(person);
    }

    public LiveData<List<Person>> getPersons() {
        return persons;
    }
}
