package com.example.projet_laurin_marc.database.entity;

import com.google.firebase.firestore.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Person {
    // --------------- Attributes ----------------
    // set columns of the database
    private String ahv;
    private String firstname;
    private String lastname;
    private String phone;
    private String birthday;
    private String zip;
    private String city;
    private String street;
    private String canton;
    private String county;

    private String id;

    // --------------- Constructor ----------------
    public Person(String ahv, String firstname, String lastname, String phone, String birthday, String zip, String city, String street, String canton, String county) {
        this.ahv = ahv;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.birthday = birthday;
        this.zip = zip;
        this.city = city;
        this.street = street;
        this.canton = canton;
        this.county = county;
    }

    public Person(){

    }

    // --------------- Getter & Setter ----------------
    // ahv, firstname, lastname, phone, birthday, address

    public String getAhv() {
        return ahv;
    }

    public void setAhv(String ahv) {
        this.ahv = ahv;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZip() {
        return zip;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }
    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ahv", ahv);
        result.put("firstname", firstname);
        result.put("lastname", lastname);
        result.put("phone", phone);
        result.put("birthday", birthday);
        result.put("zip", zip);
        result.put("city", city);
        result.put("county", county);
        result.put("canton", canton);
        return result;
    }

}
