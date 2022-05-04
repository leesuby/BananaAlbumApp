package com.example.bananaalbum.model;

import java.io.Serializable;

public class Album implements Serializable {
    private String name;
    private String location_created;
    private String date_created;


    public Album(String name) {
        this.name = name;
    }
    public Album(String name,String location,String date) {
        this.name = name;
        this.location_created =location;
        this.date_created =date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation_created() {
        return location_created;
    }

    public String getDate_created() {
        return date_created;
    }
}
