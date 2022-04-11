package com.example.bananaalbum.model;

import java.io.Serializable;

public class Picture implements Serializable {

    private int resourceId;

    public Picture(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}
