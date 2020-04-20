package com.example.projet_laurin_marc.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "canton", indices = {@Index(value = {"name"}, unique = true)})
public class Canton {
    // --------------- Attributes ----------------
    // set columns of the database
    @PrimaryKey
    @NonNull
    private String name;


    // --------------- Constructors ----------------
    public Canton(String name) {
        this.name = name;
    }

    public Canton() {
    } // default

    // --------------- Getter & Setter ----------------


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Canton)) return false;
        Canton o = (Canton) obj;
        return o.getName().equals(this.getName());
    }

    @Override
    public String toString() {
        return name;
    }
}
