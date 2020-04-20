package com.example.projet_laurin_marc.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "county",  primaryKeys = {"name"},foreignKeys =
@ForeignKey(
        entity = Canton.class,
        parentColumns = "name",
        childColumns = "canton",
        onDelete = ForeignKey.NO_ACTION
        ),
        indices = {
                @Index(
                        value = {"canton"}
)}
)
public class County {
    // --------------- Attributes ----------------
    // set columns of the database
    @NonNull
    private int zip;

    @NonNull
    private String name;

    @NonNull
    private String canton;


    // --------------- Constructors ----------------
    public County(int zip, String name, String canton) {
        this.zip = zip;
        this.name = name;
        this.canton = canton;
    }

    public County() {
    } // default

    // --------------- Getter & Setter ----------------

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof County)) return false;
        County o = (County) obj;
        if (o.getZip() == this.getZip())
            return true;
        return false;
    }

    @Override
    public String toString() {
        return name;
    }
}
