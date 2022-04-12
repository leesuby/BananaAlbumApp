package com.example.bananaalbum.model;

import java.io.Serializable;

public class Picture implements Serializable {

    private int resourceId;
    private boolean isEditMode = false;
    private boolean isChoosen = false;

    public Picture(int resourceId) {
        this.resourceId = resourceId;
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
}
