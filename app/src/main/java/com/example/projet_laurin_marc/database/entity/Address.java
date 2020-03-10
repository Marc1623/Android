package com.example.projet_laurin_marc.database.entity;

import androidx.room.ColumnInfo;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "address")
public class Address {
    // --------------- Attributes ----------------
    // set columns of the database
    @PrimaryKey(autoGenerate = true) //should be zip+city+street = unique always!
    private int id;
    //foreign key to the address from the person
    @Embedded
    private Person person;

    @ColumnInfo(name = "zip")
    private int zip;

    @ColumnInfo(name = "city")
    private String city;

    @ColumnInfo(name = "street")
    private String street;

    // --------------- Constructor ----------------
    public Address(int zip, String city, String street, Person person) {
        this.zip = zip;
        this.city = city;
        this.street = street;
        this.person = person;
    }
    public Address(){}

    // --------------- Getter & Setter ----------------
    //id, person, zip, city, street


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
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
}
