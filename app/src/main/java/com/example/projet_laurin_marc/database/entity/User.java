package com.example.projet_laurin_marc.database.entity;

import com.google.firebase.firestore.Exclude;

import java.util.HashMap;
import java.util.Map;

public class User {
    // --------------- Attributes ----------------
    // set columns of the database

    private String id;

    private String email;

    private String pwd;

    private String canton;

    private String county;

    // --------------- Constructors ----------------
    public User(String email, String pwd, String canton, String county) {
        this.email = email;
        this.pwd = pwd;
        this.canton = canton;
        this.county = county;
    }
    public User(){
        /*this.email = "newUser@test.com";
        this.pwd = "123456";
        this.canton = "Bern";
        this.county = "Krattigen";*/
    } // default

    // --------------- Getter & Setter ----------------
   // id, email, pwd
    @Exclude
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("pwd", pwd);
        result.put("canton", canton);
        result.put("county", county);
        return result;
    }
}
