package com.example.restaurantmanager.model;

public class Comment {
    private String id;
    private String name;
    private String title;
    private String time;

    public Comment(String id, String name, String title, String time) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
