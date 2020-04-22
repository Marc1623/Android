package com.example.projet_laurin_marc.database.entity;

import com.google.firebase.firestore.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Canton {
    // --------------- Attributes ----------------
    // set columns of the database

    private String id;

    private String name;


    // --------------- Constructors ----------------
    public Canton(String name) {
        this.name = name;
    }

    public Canton(){}

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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        return result;
    }
}