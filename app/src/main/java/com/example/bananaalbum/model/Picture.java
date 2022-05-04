package com.example.bananaalbum.model;

import java.io.Serializable;
import java.net.URI;

public class Picture implements Serializable {

    private int resourceId;
    private boolean isEditMode = false;
    private boolean isChoosen = false;
    private String uri;

    public Picture(int resourceId) {
        this.resourceId = resourceId;
    }
    public Picture(String uri){
        this.uri = uri;
    }

    public int getResourceId() {
        return resourceId;
    }

    public boolean isChoosen() {
        return isChoosen;
    }

    public boolean isEditMode() {
        return isEditMode;
    }

    public void setEditMode(boolean editMode) {
        isEditMode = editMode;
    }

    public void setChoosen(boolean choosen) {
        isChoosen = choosen;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }


    public String getUri() {
        return uri;
    }
}
