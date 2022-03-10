package com.example.bananaalbum.model;

//Model
public class Test {
    private int imageAvatar;
    private String name;
    private String description;

    public Test(int imageAvatar, String name, String description) {
        this.imageAvatar = imageAvatar;
        this.name = name;
        this.description = description;
    }

    public int getImageAvatar() {
        return imageAvatar;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setImageAvatar(int imageAvatar) {
        this.imageAvatar = imageAvatar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
