package com.example.projet_laurin_marc.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "user", indices = {@Index(value = {"email"}, unique = true)})
public class User {
    // --------------- Attributes ----------------
    // set columns of the database
    @PrimaryKey (autoGenerate = true)
    private int id;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "pwd")
    private String pwd;

    @ColumnInfo(name = "canton")
    private String canton;

    @ColumnInfo(name = "county")
    private String county;

    // --------------- Constructors ----------------
    public User(String email, String pwd, String canton, String county) {
        this.email = email;
        this.pwd = pwd;
        this.canton = canton;
        this.county = county;
    }
    public User(){} // default

    // --------------- Getter & Setter ----------------
   // id, email, pwd
    public int getId() {
        return id;
    }
    public void setId(int id) {
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
}
