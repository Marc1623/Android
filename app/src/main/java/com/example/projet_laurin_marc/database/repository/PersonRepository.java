package com.example.projet_laurin_marc.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.projet_laurin_marc.BaseApp;
import com.example.projet_laurin_marc.database.async.person.CreatePerson;
import com.example.projet_laurin_marc.database.async.person.DeletePerson;
import com.example.projet_laurin_marc.database.async.person.UpdatePerson;

import com.example.projet_laurin_marc.database.entity.County;
import com.example.projet_laurin_marc.database.entity.Person;
import com.example.projet_laurin_marc.database.entity.User;
import com.example.projet_laurin_marc.database.util.OnAsyncEventListener;

import java.util.List;


public class PersonRepository {


    private static PersonRepository instance;

    private PersonRepository() {
    }

    public static PersonRepository getInstance() {
        if (instance == null) {
            synchronized (County.class) {
                if (instance == null) {
                    instance = new PersonRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<Person> getPerson(final String personId, Application application) {
        return ((BaseApp) application).getDatabase().personDao().getById(personId);
    }

    public void insert(final Person person, OnAsyncEventListener callback,
                       Application application) {
        new CreatePerson(application, callback).execute(person);
    }

    public void update(final Person person, OnAsyncEventListener callback,
                       Application application) {
        new UpdatePerson(application, callback).execute(person);
    }

    public void delete(final Person person, OnAsyncEventListener callback,
                       Application application) {
        new DeletePerson(application, callback).execute(person);
    }

    // ----------------------------------------------------------
    // All Persons
    public LiveData<List<Person>> getAllPersons(Application application) {
        return ((BaseApp) application).getDatabase().personDao().getAll();
    }

    public LiveData<List<Person>> getAllPersonsByCounty(String county, Application application) {
        return ((BaseApp) application).getDatabase().personDao().getAllByCounty(county);
    }
}
