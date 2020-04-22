package com.example.projet_laurin_marc.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.projet_laurin_marc.database.entity.Person;
import com.example.projet_laurin_marc.database.firebase.PersonLiveData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class PersonRepository {


    private LiveData <List<Person>> allPersons;

    public PersonRepository(Application app){
        allPersons = getAllPersons();
    }

    public void insert(Person person){
        String id = FirebaseDatabase.getInstance().getReference("persons").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("persons")
                .child(id)
                .setValue(person);
    }

    public void update(Person person){
        FirebaseDatabase.getInstance()
                .getReference("persons")
                .child(person.getId())
                .updateChildren(person.toMap());
    }

    public void delete(Person person){
        FirebaseDatabase.getInstance()
                .getReference("persons")
                .child(person.getId())
                .removeValue();
    }

    public LiveData<List<Person>> getAllPersons() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("persons");
        return new PersonLiveData(reference);
    }
}
