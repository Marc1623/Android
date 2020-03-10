package com.example.projet_laurin_marc.database.entity;


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
    private int ahv;
    //foreign key to the address from the person
    @Embedded
    private Address address;

    @ColumnInfo(name = "firstname")
    private String firstname;

    @ColumnInfo(name = "lastname")
    private String lastname;

    @ColumnInfo(name = "phone")
    private int phone;

    @ColumnInfo(name = "birthday")
    private Date birthday;

    // --------------- Constructor ----------------
    public Person(int ahv, String firstname, String lastname, int phone, Date birthday ) {
        this.ahv = ahv;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.birthday = birthday;
    }
    public Person(){}

    // --------------- Getter & Setter ----------------
    // ahv, firstname, lastname, phone, birthday, address
    public int getAhv() {
        return ahv;
    }

    public void setAhv(int ahv) {
        this.ahv = ahv;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
