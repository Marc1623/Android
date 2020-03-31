package com.example.projet_laurin_marc.database.entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "person")
public class Person {
    // --------------- Attributes ----------------
    // set columns of the database
    @PrimaryKey
    @NonNull
    private String ahv;
    //foreign key to the address from the person.. had no time to solve problem..
    // @Embedded
    //private Address address;

    @ColumnInfo(name = "firstname")
    private String firstname;

    @ColumnInfo(name = "lastname")
    private String lastname;

    @ColumnInfo(name = "phone")
    private String phone;

    @ColumnInfo(name = "birthday")
    private String birthday;

    @ColumnInfo(name = "zip")
    private String zip;

    @ColumnInfo(name = "city")
    private String city;

    @ColumnInfo(name = "street")
    private String street;

    @ColumnInfo(name = "canton")
    private String canton;

    @ColumnInfo(name = "county")
    private String county;

    // --------------- Constructor ----------------
    public Person(@NonNull String ahv, String firstname, String lastname, String phone, String birthday, String zip, String city, String street, String canton, String county) {
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

   /* public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }*/

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
}
