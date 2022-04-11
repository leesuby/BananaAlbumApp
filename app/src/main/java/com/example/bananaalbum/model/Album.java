package com.example.bananaalbum.model;

import java.io.Serializable;

public class Album implements Serializable {
    private String name;


    public Album(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
