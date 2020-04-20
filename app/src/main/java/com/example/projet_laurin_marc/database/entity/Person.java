package com.example.projet_laurin_marc.database.entity;


import androidx.annotation.NonNull;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;

import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "person", primaryKeys = {"ahv"},
        foreignKeys = {
                @ForeignKey(
                        entity = County.class,
                        parentColumns = "name",
                        childColumns = "county",
                        onDelete = ForeignKey.NO_ACTION
                )},
        indices = {
                @Index(
                        value = {"county"}
                )})
public class Person implements Comparable {
    // --------------- Attributes ----------------
    // set columns of the database
    @NonNull
    private String ahv;
    private String firstName;
    private String lastName;
    private String phone;
    private String birthday;
    private String street;
    private String county;

    @Ignore
    public Person() {
    }//Default

    // --------------- Constructor ----------------
    public Person(@NonNull String ahv, String firstName, String lastName, String phone, String birthday, String street, String county) {
        this.ahv = ahv;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.birthday = birthday;
        this.street = street;
        this.county = county;
    }
    // --------------- Getter & Setter ----------------


    @NonNull
    public String getAhv() {
        return ahv;
    }

    public void setAhv(@NonNull String ahv) {
        this.ahv = ahv;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Person)) return false;
        Person o = (Person) obj;
        return o.getAhv().equals(this.getAhv());
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return toString().compareTo(o.toString());
    }
}
