package com.example.projet_laurin_marc.database.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


import com.example.projet_laurin_marc.BaseApp;
import com.example.projet_laurin_marc.database.entity.Person;
import com.example.projet_laurin_marc.database.entity.User;
import com.example.projet_laurin_marc.database.repository.PersonRepository;
import com.example.projet_laurin_marc.database.util.OnAsyncEventListener;

import java.util.List;

public class PersonViewModel extends AndroidViewModel {

    private PersonRepository repository;

    private Application application;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<Person> observablePerson;
    private final MediatorLiveData<List<Person>> observablePersons;
    private final MediatorLiveData<List<Person>> observablePersonsByCounty;

    public PersonViewModel(@NonNull Application application,
                         final String personId,final String county, PersonRepository personRepository) {
        super(application);

        this.application = application;

        repository = personRepository;

        observablePerson = new MediatorLiveData<>();
        observablePersons = new MediatorLiveData<>();
        observablePersonsByCounty = new MediatorLiveData<>();

        // set by default null, until we get data from the database.
        observablePerson.setValue(null);
        observablePersons.setValue(null);
        observablePersonsByCounty.setValue(null);

        LiveData<Person> person = repository.getPerson(personId, application);
        LiveData<List<Person>> persons = repository.getAllPersons(application);
        LiveData<List<Person>> personsByCounty = repository.getAllPersonsByCounty(county, application);

        // observe the changes of the client entity from the database and forward them
        observablePerson.addSource(person, observablePerson::setValue);
        observablePersons.addSource(persons, observablePersons::setValue);
        observablePersonsByCounty.addSource(personsByCounty, observablePersonsByCounty::setValue);
    }

    public LiveData<Person> getPerson() {
        return observablePerson;
    }

    public LiveData<List<Person>> getAllPersons() {
        return observablePersons;
    }

    public LiveData<List<Person>> getAllPersonsByCounty() {
        return observablePersonsByCounty;
    }

    public void createPerson(Person person, OnAsyncEventListener callback) {
        repository.insert(person, callback, application);
    }

    public void updatePerson(Person person, OnAsyncEventListener callback) {
        repository.update(person, callback, application);
    }

    public void deletePerson(Person person, OnAsyncEventListener callback) {
        repository.delete(person, callback, application);
    }
}
