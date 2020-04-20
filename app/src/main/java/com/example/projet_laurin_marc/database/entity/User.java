package com.example.projet_laurin_marc.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "user",
        foreignKeys =
        @ForeignKey(
                entity = County.class,
                parentColumns = "name",
                childColumns = "county",
                onDelete = ForeignKey.NO_ACTION
        ),
        indices = {
                @Index(
                        value = {"county"}
                )})
public class User {
    // --------------- Attributes ----------------
    // set columns of the database
    @PrimaryKey
    @NonNull
    private String email;
    private String pwd;
    private String county;


    // --------------- Constructors ----------------
    public User(String email, String pwd, String county) {
        this.email = email;
        this.pwd = pwd;
        this.county = county;
    }
    public User(){
           } // default

    // --------------- Getter & Setter ----------------

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof User)) return false;
        User o = (User) obj;
        return o.getEmail().equals(this.getEmail());
    }

    @Override
    public String toString() {
        return email;
    }

}
