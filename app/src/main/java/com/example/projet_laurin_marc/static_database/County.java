package com.example.projet_laurin_marc.static_database;

public class County {

    private int id;
    private String canton;
    private String county;

    public County(String canton, String county) {
        this.id = 0;
        this.canton = canton;
        this.county = county;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return county;
    }
}
