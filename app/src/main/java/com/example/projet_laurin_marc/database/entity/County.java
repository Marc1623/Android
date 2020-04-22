package com.example.projet_laurin_marc.database.entity;

import com.google.firebase.firestore.Exclude;

import java.util.HashMap;
import java.util.Map;

public class County {
    // --------------- Attributes ----------------
    // set columns of the database

    private String id;
    private String name;
    private String owner;


    // --------------- Constructors ----------------
    public County(String name) {
        this.name = name;
    }

    // --------------- Getter & Setter ----------------
   // id, email, pwd
    @Exclude
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        return result;
    }
    @Override
    public String toString() {
        return name;
    }
}
